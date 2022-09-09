package org.nutz.spring.boot.dao;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import lombok.RequiredArgsConstructor;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
@AutoConfiguration
@ConditionalOnBean({Dao.class})
@ConditionalOnExpression("${nutz.dao.enabled:true}")
@EnableConfigurationProperties(NutzDaoAutoConfigurationProperties.class)
@AutoConfigureAfter({NutzDaoAutoConfiguration.class})
@RequiredArgsConstructor
public class NutzDatabaseInitializer {

    private final Dao dao;

    private final NutzDaoAutoConfigurationProperties properties;

    @PostConstruct
    public void create() {
        Daos.CHECK_COLUMN_NAME_KEYWORD = properties.getGlobal().isCheckColumnNameKeyword();
        Daos.FORCE_HUMP_COLUMN_NAME = properties.getGlobal().isForceHumpColumnName();
        Daos.FORCE_UPPER_COLUMN_NAME = properties.getGlobal().isForceUpperColumnName();
        Daos.FORCE_WRAP_COLUMN_NAME = properties.getGlobal().isForceWrapColumnName();
        Daos.DEFAULT_VARCHAR_WIDTH = properties.getGlobal().getDefaultVarcharWidth();
        boolean create = properties.getRuntime().isCreate();
        boolean migration = properties.getRuntime().isMigration();
        Arrays.stream(properties.getRuntime().getBasepackage()).forEach(pkg -> {
            if (create) {
                Daos.createTablesInPackage(dao, pkg, properties.getRuntime().isFoceCreate());
            }
            if (migration) {
                Daos.migration(dao,
                               pkg,
                               properties.getRuntime().isAddColumn(),
                               properties.getRuntime().isDeleteColumn(),
                               properties.getRuntime().isCheckIndex());
            }
        });
    }
}
