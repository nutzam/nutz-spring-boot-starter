package tech.riemann.demo.controller.wechat;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import me.chanjar.weixin.mp.api.WxMpService;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author wkipy
 *
 */
@RestController
public class WechatController {

    @Autowired
    WxMpService wxMpService;

    @Autowired
    HttpServletResponse response;

    @GetMapping("wechat")
    @ApiIgnore
    public void check(String timestamp, String signature, String nonce, String echostr) throws IOException {
        if (wxMpService.checkSignature(timestamp, nonce, signature)) {
            response.getWriter().write(echostr);
        }
    }

}