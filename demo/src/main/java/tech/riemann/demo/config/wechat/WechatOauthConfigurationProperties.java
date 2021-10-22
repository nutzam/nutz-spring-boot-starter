package tech.riemann.demo.config.wechat;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author wkipy
 *
 */
@Data
@ConfigurationProperties("wechat.oauth")
public class WechatOauthConfigurationProperties {

    Server server = new Server();

    Client mp = new Client();

    Client miniapp = new Client();

    Client pc = new Client();

    @Data
    public class Client {
        String domain;
        boolean ssl = false;
        String router = "/wechat/oauth";

        public String getUrl(String state) {
            return String.format("%s://%s/index.html/#%s?state=%s", ssl ? "https" : "http", domain, router, state);
        }
    }

    @Data
    public class Server {
        String domain;
        boolean ssl = false;

        public String getUrl() {
            return String.format("%s://%s", ssl ? "https" : "http", domain);
        }
    }

}
