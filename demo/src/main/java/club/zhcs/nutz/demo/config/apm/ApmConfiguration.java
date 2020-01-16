package club.zhcs.nutz.demo.config.apm;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import club.zhcs.apm.URLProvider;
import club.zhcs.apm.UserCollector;
import club.zhcs.auth.AuthService;

/**
 * 
 * @author kerbores(kerbores@gmail.com)
 */
@Configuration
public class ApmConfiguration {

    @Bean
    public UserCollector userCollector(AuthService authService) {
        return authService::userName;
    }

    @Bean
    public URLProvider urlProvider(HttpServletRequest request) {
        return request::getRequestURI;
    }

}
