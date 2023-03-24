package org.nutz.spring.boot.request;

import java.util.List;

import org.nutz.lang.Lang;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@Data
@ConfigurationProperties(prefix = "nutz.http")
public class NutzHttpAutoConfigurationProperties {

    /**
     * 是否启用
     */
    boolean enabled = false;

    /**
     * sender http 配置
     */
    Http http = new Http();

    /**
     * 代理配置
     */
    Proxy proxy = new Proxy();

    @Data
    public class Http {
        /**
         * 连接超时时间
         */
        int connectionTimeout = 30 * 1000;

        /**
         * 读取超时时间
         */
        int timeout = 10 * 60 * 1000;

        /**
         * 支持重定向跟随
         */
        boolean followRedirects = true;

        /**
         * 开启ssl证书验证
         */
        boolean jvmHttpsCheck = true;
    }

    /**
     * 代理配置
     * 
     * @author kerbores
     *
     */
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
