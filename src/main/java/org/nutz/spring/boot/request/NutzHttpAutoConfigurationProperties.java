package org.nutz.spring.boot.request;

import java.util.List;

import org.nutz.lang.Lang;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author kerbores
 *
 */
@Data
@ConfigurationProperties(prefix = "nutz.http")
public class NutzHttpAutoConfigurationProperties {

    /**
     * 是否启用
     */
    boolean enabled = true;

    long connectionTimeout;

    boolean followRedirects = true;

    /**
     * 代理配置
     */
    Proxy proxy = new Proxy();

    @Data
    public class Proxy {
        /**
         * 是否启用代理
         */
        boolean enabled = false;
        /**
         * 代理主机
         */
        String host;
        /**
         * 代理端口
         */
        int port;
        /**
         * 被代理的主机列表
         */
        List<String> proxiedHosts = Lang.list();
    }

}
