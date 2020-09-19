package org.nutz.spring.boot.ngrok;

import org.nutz.plugins.ngrok.client.NgrokClient;
import org.nutz.plugins.ngrok.server.NgrokServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
@Configuration
@ConditionalOnClass(NgrokClient.class)
@EnableConfigurationProperties(NgrokAutoConfigurationProperties.class)
public class NgrokAutoConfiguration {

    @Value("${server.port}")
    int port;

    @Bean(initMethod = "start", destroyMethod = "stop")
    @ConditionalOnProperty(name = "nutz.ngrok.client.token")
    public NgrokClient ngrokClient(NgrokAutoConfigurationProperties ngrokConfigProperties) {
        NgrokClient client = new NgrokClient();
        client.to_port = getPort(ngrokConfigProperties);
        client.auth_token = ngrokConfigProperties.getClient().getToken();
        client.srv_host = ngrokConfigProperties.getClient().getServer();
        client.srv_port = ngrokConfigProperties.getClient().getServerPort();
        return client;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    @ConditionalOnExpression("${nutz.ngrok.server.enabled:false}")
    public NgrokServer ngrokServer(NgrokAutoConfigurationProperties config) {
        NgrokServer server = new NgrokServer();
        server.ssl_jks_password = config.getServer().getSslJksPassword();
        server.ssl_jks_path = config.getServer().getSslJksPath();
        server.srv_host = config.getServer().getHost();
        server.srv_port = config.getServer().getPort();
        server.http_port = config.getServer().getHttpPort();
        server.redis = config.getServer().isRedis();
        server.redis_host = config.getServer().getRedisHost();
        server.redis_port = config.getServer().getRedisPort();
        server.redis_key = config.getServer().getRedisKey();
        server.debug = config.getServer().isDebug();
        return server;
    }

    /**
     * @param ngrokConfigProperties
     * @return
     */
    private int getPort(NgrokAutoConfigurationProperties ngrokConfigProperties) {
        if (ngrokConfigProperties.getClient().getPort() != 0) {
            return ngrokConfigProperties.getClient().getPort();
        }
        return port == 0 ? 8080 : port;
    }
}
