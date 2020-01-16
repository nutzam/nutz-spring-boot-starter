package org.nutz.spring.boot.service;

import javax.annotation.Resource;

import org.nutz.dao.Dao;
import org.nutz.service.IdEntityService;
import org.nutz.spring.boot.service.entity.Entity;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
public class IdBaseService<T extends Entity> extends IdEntityService<T> implements ExtService<T> {

    @Resource(type = Dao.class)
    public void init(Dao dao) {
        super.setDao(dao);
    }

}
