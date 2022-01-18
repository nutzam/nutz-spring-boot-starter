package org.nutz.spring.boot.dao;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.nutz.dao.Dao;
import org.nutz.dao.DaoInterceptor;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.impl.DaoRunner;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.impl.NutDao;
import org.nutz.resource.Scans;
import org.nutz.spring.boot.dao.NutzDaoAutoConfigurationProperties.SqlManager.Mode;
import org.nutz.spring.boot.dao.sqlmanager.XmlSqlManager;
import org.nutz.spring.boot.dao.sqltpl.impl.beetl.BeetlSqlTpl;
import org.nutz.spring.boot.dao.sqltpl.impl.freemarker.FreeMarkerSqlTpl;
import org.nutz.spring.boot.dao.sqltpl.impl.jetbrick.JetbrickSqlTpl;
import org.nutz.spring.boot.dao.sqltpl.impl.velocity.VelocitySqlTpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
@Configuration
@ConditionalOnClass({Dao.class})
@ConditionalOnExpression("${nutz.dao.enabled:true}")
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(NutzDaoAutoConfigurationProperties.class)
@Import(SpringResourceLoactionConfiguration.class)
public class NutzDaoAutoConfiguration {

    @Autowired
    NutzDaoAutoConfigurationProperties properties;

    @Autowired
    SpringResourceLoaction springResourceLoaction;

    @PostConstruct
    public void initSqlTemplate() {
        Scans.me().addResourceLocation(springResourceLoaction);
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

    @Bean
    @ConditionalOnMissingBean(DaoRunner.class)
    public DaoRunner daoRunner() {
        return new SpringDaoRunner();
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

    @Bean
    public Dao dao(DataSource dataSource, SqlManager sqlManager, DaoRunner daoRunner, ApplicationContext context) {
        NutDao dao = new NutDao(dataSource, sqlManager);
        dao.setRunner(daoRunner);

        Map<String, DaoInterceptor> interceptors = context.getBeansOfType(DaoInterceptor.class);
        interceptors.values().forEach(dao::addInterceptor);

        if (properties.getInterceptor().isTime()) {
            dao.addInterceptor("time");
        }
        return dao;
    }

}
