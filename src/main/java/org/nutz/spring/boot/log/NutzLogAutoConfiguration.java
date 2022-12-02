package org.nutz.spring.boot.log;

import org.nutz.lang.Strings;
import org.nutz.log.LogAdapter;
import org.nutz.log.Logs;
import org.nutz.log.impl.Log4jLogAdapter;
import org.nutz.log.impl.Slf4jLogAdapter;
import org.nutz.log.impl.SystemLogAdapter;
import org.nutz.plugin.SimplePluginManager;
import org.nutz.spring.boot.log.NutzLogConfigurationProperties.Type;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@AutoConfiguration
@EnableConfigurationProperties(NutzLogConfigurationProperties.class)
@RequiredArgsConstructor
public class NutzLogAutoConfiguration {

    private final NutzLogConfigurationProperties config;

    @PostConstruct
    public void init() {
        /**
         * 非自定义
         */
        if (config.getType() != Type.CUSTOMER) {
            Logs.setAdapter(new SimplePluginManager<LogAdapter>(
                                                                config.getType().getClazz(),
                                                                Slf4jLogAdapter.class,
                                                                Log4jLogAdapter.class,
                                                                SystemLogAdapter.class).get());
        } else if (Strings.isNotBlank(config.getCustomerLogAdapter())) {// 自定义且设置了适配器
            String packageName = Logs.class.getPackage().getName() + ".impl.";
            Logs.setAdapter(new SimplePluginManager<LogAdapter>(
                                                                config.getCustomerLogAdapter(),
                                                                packageName + "Slf4jLogAdapter",
                                                                packageName + "Log4jLogAdapter",
                                                                packageName + "SystemLogAdapter").get());
        } else {// 默认实现
            String packageName = Logs.class.getPackage().getName() + ".impl.";
            Logs.setAdapter(new SimplePluginManager<LogAdapter>(
                                                                packageName
                                                                + "CustomLogAdapter",
                                                                packageName + "Slf4jLogAdapter",
                                                                packageName + "Log4jLogAdapter",
                                                                packageName + "SystemLogAdapter").get());
        }
    }

}
