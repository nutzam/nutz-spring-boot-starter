package org.nutz.spring.boot.service.interfaces;

import java.io.Serializable;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 * @param <T>
 *            实体类型
 */
public interface IdNameEntityService<T extends Serializable> extends IdEntityService<T>, NameEntityService<T> {

}
