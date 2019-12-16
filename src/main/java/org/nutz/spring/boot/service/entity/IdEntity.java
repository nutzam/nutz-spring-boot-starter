package org.nutz.spring.boot.service.entity;

import org.nutz.dao.entity.annotation.Id;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
public class IdEntity extends Entity {

    private static final long serialVersionUID = 1L;

    @Id
    long id;

}
