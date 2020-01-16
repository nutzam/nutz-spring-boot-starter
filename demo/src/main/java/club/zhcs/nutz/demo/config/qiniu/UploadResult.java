package club.zhcs.nutz.demo.config.qiniu;

import org.nutz.json.JsonField;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
public class UploadResult {

    public static UploadResult create() {
        return new UploadResult();
    }
    public static UploadResult create(String domain, String key) {
        return create().setDomain(domain).setKey(key);
    }
    String domain;

    NutMap ext = NutMap.NEW();

    String key;

    public UploadResult ext(String key, Object obj) {
        this.ext.setv(key, obj);
        return this;
    }

    public String getDomain() {
        return domain;
    }

    public NutMap getExt() {
        return ext;
    }

    public String getKey() {
        return key;
    }

    @JsonField
    public String getUrl() {
        return String.format(domain.startsWith("http") ? "%s/%s" : "http://%s/%s", domain, key);
    }

    public UploadResult setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public void setExt(NutMap ext) {
        this.ext = ext;
    }

    public UploadResult setKey(String key) {
        this.key = key;
        return this;
    }

    public void setUrl(String url) {
        throw Lang.makeThrow("");
    }
}
