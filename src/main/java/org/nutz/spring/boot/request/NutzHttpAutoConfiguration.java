package org.nutz.spring.boot.request;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;

import org.nutz.http.ProxySwitcher;
import org.nutz.http.Request;
import org.nutz.lang.Lang;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(NutzHttpAutoConfigurationProperties.class)
@ConditionalOnExpression("${nutz.http.enabled:true}")
public class NutzHttpAutoConfiguration {

    private final NutzHttpAutoConfigurationProperties config;

    private final ApplicationContext applicationContext;

    public boolean hasBean(Class<? extends Object> clazz) {
        try {
            return applicationContext.getBean(clazz) != null;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Bean
    @ConditionalOnExpression("${nutz.http.proxy.enabled:false}")
    public Proxy proxy() {
        return new Proxy(Type.HTTP, new InetSocketAddress(config.getProxy().getHost(), config.getProxy().getPort()));
    }

    @Bean
    @ConditionalOnExpression("${nutz.http.proxy.enabled:false}")
    public ProxySwitcher proxySwitcher(Proxy proxy) {

        return new ProxySwitcher() {

            @Override
            public Proxy getProxy(Request req) {
                return getProxy(req.getUrl());
            }

            @Override
            public Proxy getProxy(URL url) {
                return !Lang.isEmpty(config.getProxy().getProxiedHosts())
                       && config.getProxy().getProxiedHosts().contains(url.getHost()) ? proxy : null;
            }
        };
    }

    @Bean
    @ConditionalOnExpression("${!nutz.http.proxy.enabled:true}")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(NutzHttpRequestFactory.builder()
                                                                           .http(config.getHttp())
                                                                           .build());
        if (hasBean(ResponseErrorHandler.class)) {
            restTemplate.setErrorHandler(applicationContext.getBean(ResponseErrorHandler.class));
        }
        return restTemplate;
    }

    @Bean
    @ConditionalOnBean(ProxySwitcher.class)
    @ConditionalOnExpression("${nutz.http.proxy.enabled:false}")
    public RestTemplate restTemplate(ProxySwitcher proxySwitcher) {
        RestTemplate restTemplate = new RestTemplate(NutzHttpRequestFactory.builder()
                                                                           .proxySwitcher(proxySwitcher)
                                                                           .http(config.getHttp())
                                                                           .build());
        if (hasBean(ResponseErrorHandler.class)) {
            restTemplate.setErrorHandler(applicationContext.getBean(ResponseErrorHandler.class));
        }
        return restTemplate;
    }

}
