package club.zhcs.nutz.demo.config.qiniu;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.lang.Tasks;
import org.nutz.lang.Times;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.web.multipart.MultipartFile;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
public class QiNiuUploader {
    String accessKey;

    Auth auth;

    String bucketname;

    String domain;

    Log logger = Logs.get();

    String secretKey;

    String token;

    UploadManager uploadManager;

    static final String FAIL_MSG = "文件上传失败，请重试";

    /**
     * @param accessKey
     * @param secretKey
     * @param bucketname
     * @param domain
     */
    public QiNiuUploader(String accessKey, String secretKey, String bucketname, String domain) {
        super();
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketname = bucketname;
        this.domain = domain;
    }

    private String ext(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    public UploadResult getUploadResult(String key) {
        return UploadResult.create(domain, key);
    }

    @PostConstruct
    public void init() {
        auth = Auth.create(accessKey, secretKey);
        uploadManager = new UploadManager(new Configuration());
        // 10分钟刷新一下 token 信息
        Tasks.scheduleAtFixedRate(() -> token = auth.uploadToken(bucketname), Times.now(), 10, TimeUnit.MINUTES);
        token = auth.uploadToken(bucketname);
    }

    public UploadResult upload(byte[] data) {
        String key = Lang.md5(new ByteArrayInputStream(data));
        try {
            Response res = uploadManager.put(data, key, token);
            if (res.isOK()) {
                return UploadResult.create(domain, key);
            } else {
                throw Lang.makeThrow(FAIL_MSG);
            }
        }
        catch (QiniuException e) {
            logger.debug(e);
            throw Lang.makeThrow(FAIL_MSG);
        }
    }

    public UploadResult upload(byte[] data, String ext) {
        String key = String.format("%s.%s", Lang.md5(new ByteArrayInputStream(data)), ext);
        try {
            Response res = uploadManager.put(data, key, token);
            if (res.isOK()) {
                return UploadResult.create(domain, key);
            } else {
                throw Lang.makeThrow(FAIL_MSG);
            }
        }
        catch (QiniuException e) {
            logger.debug(e);
            throw Lang.makeThrow(FAIL_MSG);
        }
    }

    /**
     * @param stream
     * @return
     */
    public UploadResult upload(InputStream stream) {
        try {
            return upload(Streams.readBytes(stream));
        }
        catch (IOException e) {
            throw Lang.wrapThrow(e);
        }
    }

    public UploadResult upload(MultipartFile file) {
        try {
            return upload(file.getBytes(), ext(file.getOriginalFilename()));
        }
        catch (IOException e) {
            throw Lang.wrapThrow(e);
        }
    }
}
