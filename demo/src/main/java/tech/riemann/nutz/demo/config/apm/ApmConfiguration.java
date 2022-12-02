package tech.riemann.nutz.demo.config.apm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import club.zhcs.apm.URLProvider;
import club.zhcs.apm.UserCollector;
import jakarta.servlet.http.HttpServletRequest;
import tech.riemann.nutz.demo.config.auth.AuthService;

/**
 * 
 * @author kerbores(kerbores@gmail.com)
 */
@Configuration
public class ApmConfiguration {

    @Bean
    public UserCollector userCollector(AuthService authService) {
        return authService::currentUserName;
    }

    @Bean
    public URLProvider urlProvider(HttpServletRequest request) {
        return request::getRequestURI;
    }

}
