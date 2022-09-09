package org.nutz.spring.boot.dao;

import java.sql.Connection;

import javax.sql.DataSource;

import org.nutz.dao.ConnCallback;
import org.nutz.dao.impl.sql.run.NutDaoRunner;
import org.nutz.lang.Lang;
import org.springframework.jdbc.datasource.DataSourceUtils;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 */
public class SpringDaoRunner extends NutDaoRunner {

    @Override
    public void _run(DataSource dataSource, ConnCallback callback) {

        Connection con = DataSourceUtils.getConnection(dataSource);
        try {
            callback.invoke(con);
        }
        catch (Exception e) {
            throw Lang.wrapThrow(e);
        }
        finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }
}
