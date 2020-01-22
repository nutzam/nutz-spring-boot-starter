package club.zhcs.nutz.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.nutz.lang.Strings;
import org.nutz.repo.Base64;
import org.springframework.stereotype.Service;

import club.zhcs.captcha.DefaultCaptchaGener;
import club.zhcs.captcha.ImageVerification;
import club.zhcs.nutz.demo.utils.CacheMap;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@Service
public class CaptchaService {

    /**
     * 此处为临时性的缓存,如果分布式部署,请使用集中式的缓存来实现
     */
    CacheMap<String, String> cache = new CacheMap<>(60000);

    /**
     * -生成图片验证码base64串
     * 
     * @param uuid
     *            验证码唯一标识
     * @param length
     *            验证码长度
     * @return 图片验证码串
     * @throws IOException
     *             写入失败时
     */
    public String imagesString(String uuid, int length) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageVerification imageVerification = new ImageVerification(length, new DefaultCaptchaGener());
        ImageIO.write(imageVerification.creatImage(), "png", baos);
        String code = imageVerification.getVerifyCode();
        cache.put(uuid, code);
        return String.format("data:image/png;base64, %s", Base64.encodeToString(baos.toByteArray(), false));
    }

    /**
     * -检查验证码
     * 
     * @param uuid
     *            验证码唯一标识
     * @param code
     *            验证码
     * @return 是否有效
     */
    public boolean check(String uuid, String code) {
        return Strings.equalsIgnoreCase(code, cache.get(uuid));
    }
}
