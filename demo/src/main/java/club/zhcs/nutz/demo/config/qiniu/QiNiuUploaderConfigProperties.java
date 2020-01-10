package club.zhcs.nutz.demo.config.qiniu;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@ConfigurationProperties(prefix = "oss.qiniu")
public class QiNiuUploaderConfigProperties {
    String accessKey;

    String bucketname;

    String domain;

    String secretKey;

    public String getAccessKey() {
        return accessKey;
    }

    public String getBucketname() {
        return bucketname;
    }

    public String getDomain() {
        return domain;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setBucketname(String bucketname) {
        this.bucketname = bucketname;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
