package tech.riemann.demo.controller.wechat;

import org.nutz.dao.Cnd;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import club.zhcs.Result;
import club.zhcs.Result.OperationState;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import tech.riemann.demo.config.wechat.WechatOauthConfigurationProperties;
import tech.riemann.demo.dto.request.SocialLoginBindDTO;
import tech.riemann.demo.dto.response.WxLogin;
import tech.riemann.demo.entity.acl.LoginChannel;
import tech.riemann.demo.entity.acl.LoginChannel.Channel;
import tech.riemann.demo.entity.acl.User;
import tech.riemann.demo.service.acl.LoginChannelService;
import tech.riemann.demo.service.acl.UserService;

/**
 * @author wkipy
 *
 */
@RequestMapping("social/login")
@RestController
@EnableConfigurationProperties(WechatOauthConfigurationProperties.class)
public class SocialLoginController {

    @Autowired
    WxMpConfigStorage configStorage;

    @Autowired
    WxMpService wxMpService;

    @Autowired
    WxMaService wxMaService;

    @Autowired
    WechatOauthConfigurationProperties config;

    @Autowired
    LoginChannelService loginChannelService;

    @Autowired
    UserService userService;

    /**
     * 
     * @param state
     * @param scope
     * @return
     */
    @GetMapping("auth-url")
    @Operation(summary = "获取网页授权地址(H5端,公众号),H5端通过此接口获取到授权地址,然后直接location跳转")
    public Result<String> authUrl(@RequestParam("state") String state,
                                  @RequestParam(required = false, value = "scope", defaultValue = "snsapi_base") @Parameter(description = "scope") String scope) {
        if (!Strings.equals(scope, "snsapi_base") && !Strings.equals(scope, "snsapi_userinfo")) {
            throw Lang.makeThrow("参数异常,scope => %s ,allowableValues are 'snsapi_base' and 'snsapi_userinfo'", scope);
        }
        return Result.success(wxMpService.getOAuth2Service().buildAuthorizationUrl(config.getMp().getUrl(), scope, state));
    }

    @GetMapping("qr-option")
    @Operation(summary = "获取扫码登录二维码选项信息,PC端调用此接口获取显示二维码参数,然后调用 "
                         + "var obj = new WxLogin({\r\n"
                         + " self_redirect:true,\r\n"
                         + " id:\"login_container\", \r\n"
                         + " appid: \"\", \r\n"
                         + " scope: \"\", \r\n"
                         + " redirect_uri: \"\",\r\n"
                         + "  state: \"\",\r\n"
                         + " style: \"\",\r\n"
                         + " href: \"\"\r\n"
                         + " });显示二维码")
    public Result<WxLogin> qrOption(String id, String state, String href) {
        return Result.success(WxLogin.builder()
                                     .id(id)
                                     .state(state)
                                     .href(href)
                                     .redirect_uri(config.getPc().getUrl())
                                     .appid(configStorage.getAppId())
                                     .build());
    }

    @GetMapping("{channel}/oauth")
    @Operation(summary = "用code进行oauth登录,各端根据前置操作获取到code调用此接口,如果已经绑定,返回token,如果没有绑定则返回openid")
    public Result<String> oauth(@Parameter(description = "授权码", required = true) @RequestParam("code") String code,
                                @Parameter(description = "授权渠道", required = true) @PathVariable("channel") Channel channel)
            throws WxErrorException {
        if (channel == Channel.MINIAPP) {
            WxMaJscode2SessionResult result = wxMaService.jsCode2SessionInfo(code);
            LoginChannel loginChannel = loginChannelService.fetch(Cnd.where(LoginChannel.Fields.openid, "=", result.getOpenid()).and(LoginChannel.Fields.channel, "=", channel));
            if (loginChannel == null) { // 用户没有绑定
                return Result.<String> builder().data(result.getOpenid()).state(OperationState.FAIL).build();
            } else { // 已经绑定
                User user = userService.fetch(loginChannel.getUserId());
                return Result.success(user.toUser().getToken());
            }
        } else {
            WxOAuth2AccessToken token = wxMpService.getOAuth2Service().getAccessToken(code);
            WxOAuth2UserInfo result = wxMpService.getOAuth2Service().getUserInfo(token, "zh-CN");
            LoginChannel loginChannel = loginChannelService.fetch(Cnd.where(LoginChannel.Fields.openid, "=", result.getOpenid()).and(LoginChannel.Fields.channel, "=", channel));
            if (loginChannel == null) { // 用户没有绑定
                return Result.fail(result.getOpenid());
            } else { // 已经绑定
                User user = userService.fetch(loginChannel.getUserId());
                return Result.success(user.toUser().getToken());
            }
        }
    }

    @PostMapping("bind")
    @Operation(summary = "绑定用户")
    public Result<String> bind(@RequestBody SocialLoginBindDTO socialLoginBindDTO) {
        // TODO check mobile and code
        User user = userService.fetch(Cnd.where(User.Fields.mobile, "=", socialLoginBindDTO.getMobile()));

        // 绑定
        loginChannelService.insert(LoginChannel.builder()
                                               .userId(user.getId())
                                               .channel(socialLoginBindDTO.getChannel())
                                               .openid(socialLoginBindDTO.getOpenid())
                                               .build());

        return Result.success(user.toUser().getToken());
    }

}
