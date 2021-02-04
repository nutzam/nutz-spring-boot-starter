package org.nutz.spring.boot.service;

import javax.annotation.Resource;

import org.nutz.dao.Dao;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.service.EntityService;
import org.nutz.spring.boot.service.entity.Entity;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
public class BaseService<T extends Entity> extends EntityService<T> implements ExtService<T> {

    public static final String EQ = "=";
    public static final String NEQ = "!=";
    public static final String LT = "<";
    public static final String GT = ">";
    public static final String IS = "is";
    public static final String IS_NOT = "is not";
    public static final String NOT = "not";
    public static final String LIKE = "like";
    public static final String IN = "in";
    public static final String LT_AND_EQ = "=<";
    public static final String GT_AND_EQ = ">=";

    protected Log logger = Logs.get();

    @Resource(type = Dao.class)
    public void init(Dao dao) {
        super.setDao(dao);
    }

}
