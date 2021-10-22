package tech.riemann.demo.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.Cnd;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import club.zhcs.Result;
import club.zhcs.Result.OperationState;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.service.WxOAuth2Service;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import springfox.documentation.annotations.ApiIgnore;
import tech.riemann.demo.config.wechat.WechatOauthConfigurationProperties;
import tech.riemann.demo.dto.request.SocialLoginBindDTO;
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
    WxOAuth2Service wxOAuth2Service;

    @Autowired
    WechatOauthConfigurationProperties config;

    @Autowired
    HttpServletResponse response;

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
    @GetMapping("mp/auth-url")
    @ApiOperation("获取网页授权地址(H5端,公众号)")
    public Result<String> authUrl(@RequestParam("state") String state,
        @RequestParam(required = false, value = "scope", defaultValue = "snsapi_base") @ApiParam(allowableValues = "snsapi_base,snsapi_userinfo") String scope) {

        if (!Strings.equals(scope, "snsapi_base") && !Strings.equals(scope, "snsapi_userinfo")) {
            throw Lang.makeThrow("参数异常,scope => %s ,allowableValues are 'snsapi_base' and 'snsapi_userinfo'", scope);
        }
        return Result.success(wxMpService.getOAuth2Service().buildAuthorizationUrl(String.format("%s/social/login/%s", config.getServer().getUrl(), "mp/oauth"), scope, state));
    }

    /**
     * 网页授权oauth入口
     * 
     * @param state
     * @param code
     * @throws WxErrorException
     * @throws IOException
     */
    @GetMapping("mp/oauth")
    @ApiIgnore
    public void oauth(@RequestParam("state") String state, @RequestParam("code") String code) throws WxErrorException, IOException {
        WxOAuth2AccessToken token = wxMpService.getOAuth2Service().getAccessToken(code);
        WxOAuth2UserInfo userInfo = wxMpService.getOAuth2Service().getUserInfo(token, "zh_CN");
        LoginChannel loginChannel = loginChannelService.fetch(Cnd.where(LoginChannel.Fields.openid, "=", userInfo.getOpenid()).and(LoginChannel.Fields.channel, "=", Channel.MP));
        if (loginChannel == null) { // 用户没有绑定
            response.addHeader("openid", userInfo.getOpenid());
            response.addCookie(new Cookie("openid", userInfo.getOpenid()));
        } else { // 已经绑定
            User user = userService.fetch(loginChannel.getUserId());
            response.addHeader("token", user.toUser().getToken());
            response.addCookie(new Cookie("token", user.toUser().getToken()));
        }
        // 跳回去
        response.sendRedirect(config.getMp().getUrl(state));
    }

    @PostMapping("bind")
    @ApiOperation("绑定用户")
    public Result<String> bind(@RequestBody SocialLoginBindDTO socialLoginBindDTO) {
        // TODO check mobile and code
        User user = userService.fetch(Cnd.where(User.Fields.mobile, "=", socialLoginBindDTO.getMobile()));

        // 绑定
        loginChannelService.save(LoginChannel.builder()
            .userId(user.getId())
            .channel(socialLoginBindDTO.getChannel())
            .openid(socialLoginBindDTO.getOpenid())
            .build());

        return Result.success(user.toUser().getToken());
    }

    @GetMapping("miniapp/oauth")
    @ApiOperation("用code进行oauth登录(miniapp 小程序)")
    public Result<String> oauth(String code) throws WxErrorException {
        WxMaJscode2SessionResult result = wxMaService.jsCode2SessionInfo(code);

        LoginChannel loginChannel =
            loginChannelService.fetch(Cnd.where(LoginChannel.Fields.openid, "=", result.getOpenid()).and(LoginChannel.Fields.channel, "=", Channel.MINIAPP));
        if (loginChannel == null) { // 用户没有绑定
            return Result.<String>builder().data(result.getOpenid()).state(OperationState.FAIL).build();
        } else { // 已经绑定
            User user = userService.fetch(loginChannel.getUserId());
            return Result.success(user.toUser().getToken());
        }
    }

    @GetMapping("pc/oauth")
    @ApiOperation("用code进行oauth登录(pc 扫码登录)")
    public Result<String> scanOauth(String code) throws WxErrorException {
        // TODO 确认
        WxMaJscode2SessionResult result = wxMaService.jsCode2SessionInfo(code);

        LoginChannel loginChannel =
            loginChannelService.fetch(Cnd.where(LoginChannel.Fields.openid, "=", result.getOpenid()).and(LoginChannel.Fields.channel, "=", Channel.MINIAPP));
        if (loginChannel == null) { // 用户没有绑定
            return Result.<String>builder().data(result.getOpenid()).state(OperationState.FAIL).build();
        } else { // 已经绑定
            User user = userService.fetch(loginChannel.getUserId());
            return Result.success(user.toUser().getToken());
        }
    }

}
