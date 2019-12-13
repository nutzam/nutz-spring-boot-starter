package org.nutz.spring.boot.service.entity;

import org.nutz.dao.entity.annotation.Id;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
public class IdEntity extends Entity {

	@Id
	long id;

}
