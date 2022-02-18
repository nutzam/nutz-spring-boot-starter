package org.nutz.spring.boot.service;

import javax.annotation.Resource;

import org.nutz.dao.Dao;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.service.NameEntityService;
import org.nutz.spring.boot.service.entity.Entity;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
public class NameBaseService<T extends Entity> extends NameEntityService<T> implements ExtService<T> {

    protected Log logger = Logs.get();

    @Resource(type = Dao.class)
    public void init(Dao dao) {
        super.setDao(dao);
    }

    /**
     * @return
     * @see org.nutz.spring.boot.service.ExtService#getEntityType()
     */
    @Override
    public Class<T> getEntityType() {
        return getEntityClass();
    }

}
