package org.nutz.spring.boot.wechat;

import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.weixin.impl.AbstractWxHandler;
import org.nutz.weixin.impl.WxApi2Impl;
import org.nutz.weixin.repo.com.qq.weixin.mp.aes.AesException;
import org.nutz.weixin.repo.com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.nutz.weixin.spi.WxApi2;
import org.nutz.weixin.spi.WxHandler;
import org.nutz.weixin.util.Wxs;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
    @ConditionalOnProperty(prefix = "nutz.wechat", name = "enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean(WxApi2.class)
    public WxApi2Impl api(WechatConfigurationProperties wechatConfigProperties) {
        return new WxApi2Impl(
                              wechatConfigProperties.getToken(),
                              wechatConfigProperties.getAppId(),
                              wechatConfigProperties.getAppSecret(),
                              null,
                              wechatConfigProperties.getEncodingAesKey());
    }

    @Bean
    @ConditionalOnBean(WxApi2Impl.class)
    @ConditionalOnMissingBean(WechatJsSdkConfig.class)
    public WechatJsSdkConfig wechatJsSdkConfig(WechatConfigurationProperties wechatConfigProperties) {
        return new WechatJsSdkConfig(wechatConfigProperties.getApis().split(","));
    }

    @Bean
    public WechatController wechatController() {
        return new WechatController();
    }

    @Bean
    @ConditionalOnMissingBean(WxHandler.class)
    public WxHandler wxHandler(WxApi2Impl api) {
        return new AbstractWxHandler() {
            Log logger = Logs.get();

            @Override
            public WXBizMsgCrypt getMsgCrypt() {
                try {
                    return new WXBizMsgCrypt(api.getToken(), api.getEncodingAesKey(), api.getAppid());
                }
                catch (AesException e) {
                    logger.debug(e);
                    throw Lang.wrapThrow(e);
                }
            }

            @Override
            public boolean check(String signature, String timestamp, String nonce, String key) {
                return Wxs.check(api.getToken(), signature, timestamp, nonce);
            }
        };
    }
}
