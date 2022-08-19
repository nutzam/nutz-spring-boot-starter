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

    Server server = new Server();

    Client client = new Client();

    @Data
    public class Server {
        boolean enabled = false;
        String sslJksPassword = "";
        String sslJksPath;
        int port = 4443;
        String host = "wendal.cn";
        int httpPort = 9080;
        boolean redis;
        String redisHost = "127.0.0.1";
        int redisPort = 6379;
        String redisKey = "ngrok";
        boolean debug;
    }

    @Data
    public class Client {
        /**
         * token
         */
        String token;

        /**
         * 代理端口，默认会使用server.port
         */
        int port;

        String server = "wendal.cn";

        int serverPort = 4443;
    }

}
