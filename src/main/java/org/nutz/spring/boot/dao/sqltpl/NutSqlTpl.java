package org.nutz.spring.boot.dao.sqltpl;

import org.nutz.dao.impl.sql.NutSql;
import org.nutz.dao.jdbc.ValueAdaptor;

/**
 * @author kerbores
 *
 */
public abstract class NutSqlTpl extends NutSql {

    private static final long serialVersionUID = 1L;

    protected boolean renderComplete;

    protected NutSqlTpl(String source) {
        super(source);
    }

    @Override
    public ValueAdaptor[] getAdaptors() {
        checkRender();
        return super.getAdaptors();
    }

    @Override
    public String toPreparedStatement() {
        checkRender();
        return super.toPreparedStatement();
    }

    @Override
    public Object[][] getParamMatrix() {
        checkRender();
        return super.getParamMatrix();
    }

    protected void checkRender() {
        if (!renderComplete) {
            render();
            renderComplete = true;
        }
    }

    protected abstract void render();
}
