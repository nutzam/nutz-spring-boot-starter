package org.nutz.spring.boot.ngrok;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
@Data
@ConfigurationProperties(prefix = "nutz.ngrok")
public class NgrokAutoConfigurationProperties {

    /**
     * token
     */
    String token;

    /**
     * 代理端口，默认会使用server.port
     */
    int port;
}
