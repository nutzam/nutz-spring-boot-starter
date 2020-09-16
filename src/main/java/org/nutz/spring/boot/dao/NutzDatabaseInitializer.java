package org.nutz.spring.boot.dao;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
@Configuration
@ConditionalOnBean({Dao.class})
@ConditionalOnProperty(prefix = "nutz.dao", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(NutzDaoAutoConfigurationProperties.class)
@AutoConfigureAfter({NutzDaoAutoConfiguration.class})
public class NutzDatabaseInitializer {

    @Autowired
    private Dao dao;

    @Autowired
    NutzDaoAutoConfigurationProperties properties;

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
