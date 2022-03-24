package org.nutz.spring.boot.service.interfaces;

import java.io.Serializable;

/**
 * @author wkipy
 *
 */
public interface IdNameEntityService<T extends Serializable> extends IdEntityService<T>, NameEntityService<T> {

}
