package club.zhcs.nutz.demo.controller;

import java.io.IOException;

import org.nutz.lang.random.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import club.zhcs.Result;
import club.zhcs.apm.APM;
import club.zhcs.nutz.demo.service.CaptchaService;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
@RestController
@APM
public class CaptchaController {

    @Autowired
    CaptchaService captchaService;

    @GetMapping("captcha")
    public Result<String> captcha(@RequestParam(name = "length", required = false, defaultValue = "4") int length) throws IOException {
        String uuid = R.UU16();
        return Result.success(captchaService.imagesString(uuid, length)).addExtData("uuid", uuid);
    }
}
