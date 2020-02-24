package org.nutz.spring.boot.wechat;

import org.nutz.weixin.impl.WxApi2Impl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kerbores
 *
 */
@Configuration
@ConditionalOnClass(WxApi2Impl.class)
@EnableConfigurationProperties(WechatConfigurationProperties.class)
public class WechatAutoConfiguration {

    @Bean(name = "wxapi")
    @ConditionalOnExpression("${nutz.wechat.enabled:true}")
    public WxApi2Impl api(WechatConfigurationProperties wechatConfigProperties) {
        return new WxApi2Impl(
                              wechatConfigProperties.getToken(),
                              wechatConfigProperties.getAppid(),
                              wechatConfigProperties.getAppsecret(),
                              null,
                              wechatConfigProperties.getEncodingAesKey());
    }

    @Bean
    @ConditionalOnBean(WxApi2Impl.class)
    public WechatJsSdkConfig wechatJsSdkConfig(WechatConfigurationProperties wechatConfigProperties) {
        return new WechatJsSdkConfig(wechatConfigProperties.getApis().split(","));
    }
}
