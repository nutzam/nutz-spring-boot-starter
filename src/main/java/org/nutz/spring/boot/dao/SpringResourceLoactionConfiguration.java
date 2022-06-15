package org.nutz.spring.boot.dao;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author wkipy
 *
 */
@AutoConfiguration
public class SpringResourceLoactionConfiguration {

    @Bean
    public SpringResourceLoaction springResourceLoaction() {
        return new SpringResourceLoaction();
    }
}
