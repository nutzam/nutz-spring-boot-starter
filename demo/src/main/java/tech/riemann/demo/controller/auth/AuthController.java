package tech.riemann.demo.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import club.zhcs.Result;
import club.zhcs.auth.AuthService;
import club.zhcs.auth.AuthService.LoginDto;
import club.zhcs.auth.AuthUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author kerbores
 *
 */
@RestController
@Api(value = "Login", tags = {"登录认证模块"})
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("login")
    @ApiOperation("用户登录")
    public Result<AuthUser> login(
                                  @ApiParam(value = "用户名", required = true) @RequestParam(value = "user", required = true) String user,
                                  @ApiParam(value = "密码", required = true) @RequestParam(value = "password", required = true) String password) {

        return Result.success(authService.login(new LoginDto(user, password)));
    }
}
