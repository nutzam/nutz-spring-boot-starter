package org.nutz.spring.boot.request;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;

import org.nutz.http.ProxySwitcher;
import org.nutz.http.Request;
import org.nutz.lang.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author kerbores
 *
 */
@Configuration
@EnableConfigurationProperties(NutzHttpAutoConfigurationProperties.class)
@ConditionalOnExpression("${nutz.http.enabled:true}")
public class NutzHttpAutoConfiguration {

    @Autowired
    NutzHttpAutoConfigurationProperties config;

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
        return new RestTemplate(NutzHttpRequestFactory.builder()
                                                      .http(config.getHttp())
                                                      .build());
    }

    @Bean
    @ConditionalOnBean(ProxySwitcher.class)
    @ConditionalOnExpression("${nutz.http.proxy.enabled:false}")
    public RestTemplate restTemplate(ProxySwitcher proxySwitcher) {
        return new RestTemplate(NutzHttpRequestFactory.builder()
                                                      .proxySwitcher(proxySwitcher)
                                                      .http(config.getHttp())
                                                      .build());
    }

}
