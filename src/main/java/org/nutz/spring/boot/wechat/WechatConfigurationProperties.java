package org.nutz.spring.boot.wechat;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author kerbores
 *
 */
@Data
@ConfigurationProperties(prefix = "nutz.wechat")
public class WechatConfigurationProperties {

    /**
     * token
     */
    String token;

    /**
     * appId
     */
    String appId;

    /**
     * appSecret
     */
    String appSecret;

    /**
     * encodingAesKey
     */
    String encodingAesKey;

    /**
     * 是否启用
     */
    boolean enabled;

    /**
     * js api 列表
     */
    String apis = "updateAppMessageShareData,updateTimelineShareData,onMenuShareTimeline,onMenuShareAppMessage,onMenuShareQQ,onMenuShareWeibo,onMenuShareQZone,startRecord,stopRecord,onVoiceRecordEnd,playVoice,pauseVoice,stopVoice,onVoicePlayEnd,uploadVoice,downloadVoice,chooseImage,previewImage,uploadImage,downloadImage,translateVoice,getNetworkType,openLocation,getLocation,hideOptionMenu,showOptionMenu,hideMenuItems,showMenuItems,hideAllNonBaseMenuItem,showAllNonBaseMenuItem,closeWindow,scanQRCode,chooseWXPay,openProductSpecificView,addCard,chooseCard,openCard";
}
