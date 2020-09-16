package org.nutz.spring.boot.filepool;

import org.nutz.filepool.FilePool;
import org.nutz.filepool.NutFilePool;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@Configuration
@EnableConfigurationProperties(FilePoolAutoConfigurationProperties.class)
public class FilePoolAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "nutz.filepool", name = "enabled", havingValue = "true", matchIfMissing = true)
    public FilePool filePool(FilePoolAutoConfigurationProperties config) {
        return new NutFilePool(config.getPath(), config.getSize());
    }
}
