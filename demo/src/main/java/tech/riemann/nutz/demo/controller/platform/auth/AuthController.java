package tech.riemann.nutz.demo.controller.platform.auth;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import club.zhcs.auth.AuthService;
import club.zhcs.auth.AuthService.LoginDto;
import club.zhcs.auth.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * 码本数据 前端控制器
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-13 09:49:04
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Login", description = "登录")
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    @Operation(summary = "登录")
    public AuthUser login(@Validated @RequestBody LoginDto login) {
        return authService.login(login);
    }

}
