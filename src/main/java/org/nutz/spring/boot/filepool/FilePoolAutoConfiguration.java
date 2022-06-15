package org.nutz.spring.boot.filepool;

import org.nutz.filepool.FilePool;
import org.nutz.filepool.NutFilePool;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@AutoConfiguration
@EnableConfigurationProperties(FilePoolAutoConfigurationProperties.class)
public class FilePoolAutoConfiguration {

    @Bean
    @ConditionalOnExpression("${nutz.filepool.enabled:true}")
    public FilePool filePool(FilePoolAutoConfigurationProperties config) {
        return new NutFilePool(config.getPath(), config.getSize());
    }
}
