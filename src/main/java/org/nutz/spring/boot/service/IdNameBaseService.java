package org.nutz.spring.boot.service;

import javax.annotation.Resource;

import org.nutz.dao.Dao;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.service.IdNameEntityService;
import org.nutz.spring.boot.service.entity.Entity;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
public class IdNameBaseService<T extends Entity> extends IdNameEntityService<T> implements ExtService<T> {

    protected Log logger = Logs.get();

    @Resource(type = Dao.class)
    public void init(Dao dao) {
        super.setDao(dao);
    }

}
