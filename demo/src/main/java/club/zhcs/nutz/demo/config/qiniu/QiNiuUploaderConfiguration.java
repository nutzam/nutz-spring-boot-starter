package club.zhcs.nutz.demo.config.qiniu;

import java.io.File;

import javax.servlet.MultipartConfigElement;

import org.nutz.lang.Files;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@Configuration
@EnableConfigurationProperties(QiNiuUploaderConfigProperties.class)
public class QiNiuUploaderConfiguration {

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        File file = Files.createDirIfNoExists("~/.upload/temp");
        factory.setLocation(file.getPath());
        return factory.createMultipartConfig();
    }

    @Bean
    @Scope("prototype")
    public QiNiuUploader qiNiuUploader(QiNiuUploaderConfigProperties properties) {
        return new QiNiuUploader(properties.getAccessKey(), properties.getSecretKey(), properties.getBucketname(), properties.getDomain());
    }

}
