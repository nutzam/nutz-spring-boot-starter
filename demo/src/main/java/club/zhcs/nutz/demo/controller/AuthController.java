package club.zhcs.nutz.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import club.zhcs.Result;
import club.zhcs.auth.AuthUser;
import club.zhcs.nutz.demo.NutzDemoApplication.Tets;
import club.zhcs.nutz.demo.config.auth.AuthService;
import club.zhcs.nutz.demo.dto.request.Login;
import club.zhcs.nutz.demo.service.CaptchaService;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	AuthService authService;

	@Autowired
	CaptchaService captchaService;

	@GetMapping("tetses")
	public Result<Tets[]> tetses() {
		return Result.success(Tets.values());
	}

	@PostMapping("login")
	public Result<AuthUser> login(@RequestBody Login login) {
		if (captchaService.check(login.getUuid(), login.getCaptcha())) {
			return Result.success(authService.login(login));
		}
		return Result.fail("验证码不正确");
	}

	@GetMapping("check")
	public Result<Void> check() {
		return authService.userName() == null ? Result.fail("token无效") : Result.success();
	}

}
