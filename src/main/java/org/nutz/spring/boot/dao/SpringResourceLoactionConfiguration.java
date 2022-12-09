package org.nutz.spring.boot.dao;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@AutoConfiguration
public class SpringResourceLoactionConfiguration {

    @Bean
     SpringResourceLoaction springResourceLoaction() {
        return new SpringResourceLoaction();
    }
}
