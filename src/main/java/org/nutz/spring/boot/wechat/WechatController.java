package org.nutz.spring.boot.wechat;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.nutz.lang.util.NutMap;
import org.nutz.weixin.spi.WxHandler;
import org.nutz.weixin.util.Wxs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

/**
 * @author kerbores
 *
 */
@Controller
public class WechatController {

    @Autowired
    WechatJsSdkConfig wechatJsSdkConfig;

    @Autowired
    WxHandler wxHandler;

    @GetMapping(value = {"wechat", "wechat/?"})
    public View getMsgIn(String key, HttpServletRequest req) throws IOException {
        return new NutzViewWrapper(Wxs.handle(wxHandler, req, key));
    }

    @PostMapping(value = {"wechat", "wechat/?"})
    public View postMsgIn(String key, HttpServletRequest req) throws IOException {
        return new NutzViewWrapper(Wxs.handle(wxHandler, req, key));
    }

    @GetMapping("config")
    public @ResponseBody NutMap config(@RequestParam("url") String url) {
        return wechatJsSdkConfig.loadConfig(url);
    }

}
