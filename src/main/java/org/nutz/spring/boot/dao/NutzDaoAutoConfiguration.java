package org.nutz.spring.boot.dao;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.nutz.dao.Dao;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.impl.DaoRunner;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.impl.NutDao;
import org.nutz.integration.spring.SpringDaoRunner;
import org.nutz.integration.spring.SpringResourceLoaction;
import org.nutz.plugins.sqlmanager.xml.XmlSqlManager;
import org.nutz.plugins.sqltpl.impl.beetl.BeetlSqlTpl;
import org.nutz.plugins.sqltpl.impl.freemarker.FreeMarkerSqlTpl;
import org.nutz.plugins.sqltpl.impl.jetbrick.JetbrickSqlTpl;
import org.nutz.plugins.sqltpl.impl.velocity.VelocitySqlTpl;
import org.nutz.resource.Scans;
import org.nutz.spring.boot.dao.NutzDaoAutoConfigurationProperties.SqlManager.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
@Configuration
@ConditionalOnClass({Dao.class})
@ConditionalOnProperty(prefix = "nutz.dao", name = "enabled", havingValue = "true", matchIfMissing = true)
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(NutzDaoAutoConfigurationProperties.class)
public class NutzDaoAutoConfiguration {

    @Autowired
    NutzDaoAutoConfigurationProperties properties;

    @Bean
    public Dao dao(DataSource dataSource, SqlManager sqlManager, DaoRunner daoRunner) {
        NutDao dao = new NutDao(dataSource, sqlManager);
        dao.setRunner(daoRunner);
        return dao;
    }

    @Bean
    @ConditionalOnMissingBean(DaoRunner.class)
    public DaoRunner daoRunner() {
        return new SpringDaoRunner();
    }

    @Bean
    public SpringResourceLoaction springResourceLoaction() {
        return new SpringResourceLoaction();
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlManager sqlManager() {
        String[] paths = properties.getSqlManager().getPaths();
        if (paths == null) {
            paths = new String[]{"sqls"};
        }
        return properties.getSqlManager().getMode() == Mode.XML ? new XmlSqlManager(paths) : new FileSqlManager(paths);
    }

    @PostConstruct
    public void initSqlTemplate() {
        Scans.me().addResourceLocation(springResourceLoaction());
        if (properties.getSqlTemplate().isEnable()) {
            switch (properties.getSqlTemplate().getType()) {
            case BEETL:
                Sqls.setSqlBorning(BeetlSqlTpl.class);
                break;
            case FREEMARKER:
                Sqls.setSqlBorning(FreeMarkerSqlTpl.class);
                break;
            case JETBRICK:
                Sqls.setSqlBorning(JetbrickSqlTpl.class);
                break;
            default:
                Sqls.setSqlBorning(VelocitySqlTpl.class);
                break;
            }
        }
    }

}
