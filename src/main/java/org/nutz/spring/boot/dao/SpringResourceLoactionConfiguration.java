package org.nutz.spring.boot.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wkipy
 *
 */
@Configuration
public class SpringResourceLoactionConfiguration {

    @Bean
    public SpringResourceLoaction springResourceLoaction() {
        return new SpringResourceLoaction();
    }
}
