package org.nutz.spring.boot.log;

import org.nutz.log.LogAdapter;
import org.nutz.log.impl.Log4jLogAdapter;
import org.nutz.log.impl.NopLog;
import org.nutz.log.impl.Slf4jLogAdapter;
import org.nutz.log.impl.SystemLogAdapter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author kerbores
 *
 */
@Data
@ConfigurationProperties("nutz.log")
public class NutzLogConfigurationProperties {

    /**
     * 日志类型
     */
    Type type = Type.LOG4J2;

    /**
     * 自定义日志适配器全限定名
     */
    String customerLogAdapter;

    @Getter
    @AllArgsConstructor
    public enum Type {
        /**
         * log4j2
         */
        LOG4J2("Log4j2", Log4j2Adapter.class),
        /**
         * log4j
         */
        LOG4J("Log4j", Log4jLogAdapter.class),
        /**
         * nop
         */
        NOP("Nop", NopLog.class),
        /**
         * slf4j
         */
        SLF4J("slf4j", Slf4jLogAdapter.class),
        /**
         * Console
         */
        CONSOLE("Console", SystemLogAdapter.class),
        /**
         * 自定义
         */
        CUSTOMER("customer", LogAdapter.class);

        /**
         * 名称
         */
        String name;
        /**
         * 适配器类型
         */
        Class<? extends LogAdapter> clazz;
    }

}
