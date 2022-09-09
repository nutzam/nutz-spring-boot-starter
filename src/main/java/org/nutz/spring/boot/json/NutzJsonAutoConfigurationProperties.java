package org.nutz.spring.boot.json;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
@Data
@ConfigurationProperties(prefix = "nutz.json")
public class NutzJsonAutoConfigurationProperties {

    /**
     * 
     * @author kerbores(kerbores@gmail.com)
     *
     */
    public enum Mode {
        /**
         * 压缩
         */
        COMPACT,
        /**
         * 完整
         */
        FULL,
        /**
         * 美化
         */
        NICE,
        /**
         * 阅读友好
         */
        FORLOOK,
        /**
         * 整洁
         */
        TIDY;
    }

    /**
     * json模式,配置此模式意味着其他属性失效
     */
    private Mode mode = Mode.COMPACT;

    /**
     * 缩进
     */
    private int indent;
    /**
     * 缩进时用的字符串
     */
    private String indentBy;
    /**
     * 是否使用紧凑模式输出
     */
    private boolean compact;
    /**
     * 是否给字段添加双引号
     */
    private boolean quoteName;

    /**
     * 是否启用nutz.json
     */
    private boolean enabled = true;

    /**
     * 是否忽略null值
     */
    private boolean ignoreNull;
    /**
     * 仅输出的字段的正则表达式
     */
    private String actived;
    /**
     * 不输出的字段的正则表达式
     */
    private String locked;

    /**
     * 分隔符
     */
    private char separator = '\"';
    /**
     * 是否自动将值应用Unicode编码
     */
    private boolean autoUnicode;
    /**
     * unicode编码用大写还是小写
     */
    private boolean unicodeLower;
    /**
     * 日期格式
     */
    private String dateFormat;

    /**
     * 遇到空值的时候写入字符串
     */
    private boolean nullAsEmtry;

    /**
     * 列表空值的时候写入字符串
     */
    private boolean nullListAsEmpty;

    /**
     * 字符串空值的时候写入字符串
     */
    private boolean nullStringAsEmpty;

    /**
     * Boolean空值作为false
     */
    private boolean nullBooleanAsFalse;

    /**
     * Number空值作0
     */
    private boolean nullNumberAsZero;

    /**
     * 不使用nutzjson解析的类的全限定名正则表达式,比如 .*springfox.*
     */
    private List<String> ignoreTypes;

    /**
     * 不使用nutzjson解析的uri正则表达式,比如 比如 .*api-docs.*
     */
    private List<String> ignoreUris;

    /**
     * 时区配置
     */
    private String timeZone;

}
