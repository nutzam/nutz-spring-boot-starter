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

    Client mp = new Client();

    Client pc = new Client();

    @Data
    public class Client {
        String domain;
        boolean ssl = false;
        String router = "/user/login";

        public String getUrl() {
            return String.format("%s://%s/index.html#%s", ssl ? "https" : "http", domain, router);
        }
    }

}
