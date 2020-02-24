package org.nutz.spring.boot.wechat;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.weixin.impl.WxApi2Impl;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * @author kerbores
 *
 */
public class WechatJsSdkConfig {

    @Autowired
    WxApi2Impl wxApi;

    public WechatJsSdkConfig(String[] apis) {
        this.apis = apis;
        init();
    }

    private String[] apis;

    Log log = Logs.get();

    LoadingCache<String, NutMap> loadingCache;

    public void init() {
        this.loadingCache = CacheBuilder.newBuilder()
                                        .expireAfterAccess(30, TimeUnit.MINUTES)
                                        .maximumSize(300)
                                        .build(new CacheLoader<String, NutMap>() {

                                            @Override
                                            public NutMap load(String url) throws Exception {
                                                log.debug("Generating WX JsSDKConfig using the key url -> "
                                                          + url);
                                                return wxApi.genJsSDKConfig(url, apis);
                                            }
                                        });
    }

    /**
     * @param url
     *            url 地址
     * @return 配置信息
     */
    public NutMap loadConfig(String url) {
        try {
            return loadingCache.get(url);
        }
        catch (ExecutionException e) {
            throw Lang.wrapThrow(e);
        }
    }
}
