package tech.riemann.nutz.demo.config.apm;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import club.zhcs.apm.URLProvider;
import club.zhcs.apm.UserCollector;
import tech.riemann.nutz.demo.config.auth.AuthService;

/**
 * 
 * @author kerbores(kerbores@gmail.com)
 */
@Configuration
public class ApmConfiguration {

    @Bean
     UserCollector userCollector(AuthService authService) {
        return authService::currentUserName;
    }

    @Bean
     URLProvider urlProvider(HttpServletRequest request) {
        return request::getRequestURI;
    }

}
