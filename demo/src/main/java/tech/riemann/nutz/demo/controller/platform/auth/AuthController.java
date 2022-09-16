package tech.riemann.nutz.demo.controller.platform.auth;

import org.nutz.lang.Lang;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import club.zhcs.auth.AuthService.LoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.config.auth.AuthService;
import tech.riemann.nutz.demo.dto.response.LoginUser;

/**
 * Auth
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

    @PostMapping("auth/login")
    @Operation(summary = "登录")
    public LoginUser login(@Validated @RequestBody LoginDto login) {
        throw Lang.noImplement();
    }

    @PostMapping("auth/logout")
    @Operation(summary = "登出")
    @ResponseStatus(HttpStatus.OK)
    public void logout() {
        throw Lang.noImplement();
    }

    @GetMapping("auth/current-user")
    @Operation(summary = "当前用户")
    public LoginUser currentUser() {
        return authService.currentUser();
    }

}
