package org.nutz.spring.boot.service;

import javax.annotation.Resource;

import org.nutz.dao.Dao;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.service.IdEntityService;
import org.nutz.spring.boot.service.entity.IdEntity;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
public class IdBaseService<T extends IdEntity> extends IdEntityService<T> implements ExtService<T> {

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
