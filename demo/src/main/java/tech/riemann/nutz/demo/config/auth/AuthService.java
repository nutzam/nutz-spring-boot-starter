package tech.riemann.nutz.demo.config.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.dto.response.LoginUser;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@Component
@RequiredArgsConstructor
public class AuthService {

    private final UserDetailsService userDetailsService;

    /**
     * 当前用户信息
     * 
     * @return
     */
    public LoginUser currentUser() {
        return LoginUser.from((JwtUser) userDetailsService.loadUserByUsername(currentUserName()));
    }

    /**
     * 当前用户名
     * 
     * @return
     */
    public String currentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
