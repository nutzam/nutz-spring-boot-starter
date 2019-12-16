package org.nutz.spring.boot.json;

import java.text.DecimalFormat;
import java.util.TimeZone;

import org.nutz.integration.spring.NutzJsonMessageConverter;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
@Configuration
@ConditionalOnClass({Json.class})
@EnableConfigurationProperties(NutzJsonAutoConfigurationProperties.class)
public class NutzJsonMessageConverterAutoConfiguration {

    @Bean
    @ConditionalOnExpression("${nutz.json.enabled:false}")
    public HttpMessageConverter<Object> nutzJsonHttpMessageConverter(NutzJsonAutoConfigurationProperties properties) {
        JsonFormat format = null;
        if (properties.getMode() != null) {// 直接模式设置
            switch (properties.getMode()) {
            case COMPACT:
                format = JsonFormat.compact();
                break;
            case FORLOOK:
                format = JsonFormat.forLook();
                break;
            case FULL:
                format = JsonFormat.full();
                break;
            case NICE:
                format = JsonFormat.nice();
                break;
            case TIDY:
                format = JsonFormat.tidy();
                break;
            default:
                format = JsonFormat.compact();
                break;
            }
        } else {
            format = Json.fromJson(JsonFormat.class, Json.toJson(properties));
        }
        if (Strings.isNotBlank(properties.getActived())) {
            format.setActived(properties.getActived());
        }
        if (Strings.isNotBlank(properties.getLocked())) {
            format.setLocked(properties.getLocked());
        }
        if (Strings.isNotBlank(properties.getDateFormat())) {
            format.setDateFormat(properties.getDateFormat());
        }
        if (Strings.isNotBlank(properties.getNumberFormat())) {
            format.setNumberFormat(new DecimalFormat(properties.getNumberFormat()));
        }
        if (Strings.isNotBlank(properties.getTimeZone())) {
            format.setTimeZone(TimeZone.getTimeZone(properties.getTimeZone()));
        }
        return new NutzJsonMessageConverter().setFormat(format).setIgnoreType(properties.getIgnoreType());
    }
}
