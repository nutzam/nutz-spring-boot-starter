package tech.riemann.nutz.demo.config.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import club.zhcs.auth.PasswordUtils;
import tech.riemann.nutz.demo.config.auth.filter.JwtAuthenticationTokenFilter;
import tech.riemann.nutz.demo.config.auth.filter.LoginAuthenticationFilter;
import tech.riemann.nutz.demo.config.auth.handler.AnonymousAuthenticationEntryPoint;
import tech.riemann.nutz.demo.config.auth.handler.LoginFailureHandler;
import tech.riemann.nutz.demo.config.auth.handler.LoginSuccessHandler;
import tech.riemann.nutz.demo.config.auth.handler.LogoutHandler;
import tech.riemann.nutz.demo.config.auth.handler.NoPermissionHandler;
import tech.riemann.nutz.demo.dto.response.LoginUser;
import tech.riemann.nutz.demo.service.acl.UserService;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new JwtUserDetailsService(userService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return PasswordUtils.randomSaltVerify(rawPassword.toString(), encodedPassword);
            }

            @Override
            public String encode(CharSequence rawPassword) {
                return PasswordUtils.randomSaltEncode(rawPassword.toString());
            }
        };
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilter(UserDetailsService userDetailsService) {
        return new JwtAuthenticationTokenFilter(userDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter,
                                           LoginAuthenticationFilter loginAuthenticationFilter,
                                           LoginSuccessHandler loginSuccessHandler,
                                           LoginFailureHandler loginFailureHandler,
                                           LogoutHandler logoutHandler,
                                           AnonymousAuthenticationEntryPoint anonymousAuthenticationEntryPoint,
                                           NoPermissionHandler noPermissionHandler)
            throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.cors();
        http.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeRequests(authorize -> authorize
                                                     .antMatchers("/index.html",
                                                                  "/v3/api-docs/**",
                                                                  "/swagger-ui.html",
                                                                  "/swagger-ui/**",
                                                                  "/webjars/**",
                                                                  "/actuator/**",
                                                                  "/swagger-customer",
                                                                  "/swagger-themes")
                                                     .permitAll()
                                                     .anyRequest()
                                                     .authenticated());
        http.formLogin(configurer -> configurer.successHandler(loginSuccessHandler).failureHandler(loginFailureHandler));
        http.logout(configurer -> configurer.logoutUrl("/auth/logout").logoutSuccessHandler(logoutHandler));

        http.exceptionHandling(configurer -> configurer.accessDeniedHandler(noPermissionHandler).authenticationEntryPoint(anonymousAuthenticationEntryPoint));

        http.addFilterBefore(loginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterAt(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AnonymousAuthenticationEntryPoint anonymousAuthenticationEntryPoint() {
        return new AnonymousAuthenticationEntryPoint();
    }

    @Bean
    LogoutHandler logoutHandler() {
        return new LogoutHandler();
    }

    @Bean
    public NoPermissionHandler noPermissionHandler() {
        return new NoPermissionHandler();
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public LoginAuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager) {
        LoginAuthenticationFilter filter = new LoginAuthenticationFilter();
        filter.setFilterProcessesUrl("/auth/login");
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                Json.toJson(response.getWriter(), LoginUser.from((JwtUser) authentication.getPrincipal()));
            }
        });
        return filter;
    }
}
