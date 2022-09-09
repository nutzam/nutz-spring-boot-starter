package org.nutz.spring.boot.service.interfaces;

import java.io.Serializable;

import org.nutz.dao.Cnd;
import org.nutz.dao.entity.EntityField;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 * @param <T>
 *            实体类型
 */
public interface NameEntityService<T extends Serializable> extends EntityService<T> {
    /**
     * 根据@Name所在的属性的值删除一条记录
     * 
     * @param name
     *            属性的值
     * @return 删除的记录数,通常是0或者1
     */
    public default int delete(String name) {
        return dao().delete(getEntityType(), name);
    }

    /**
     * 根据@Name所在的属性的值获取一个实体对象
     * 
     * @param name
     *            属性的值
     * @return 实体对象,若没有符合条件的记录,则返回null
     */
    public default T fetch(String name) {
        return dao().fetch(getEntityType(), name);
    }

    /**
     * 是否存在@Name所在的属性与指定值相符的记录
     * 
     * @param name
     *            属性的值
     * @return true,如果存在符合条件的记录
     */
    public default boolean exists(String name) {
        EntityField ef = getEntity().getNameField();
        if (null == ef)
            return false;
        return dao().count(getEntityType(), Cnd.where(ef.getName(), "=", name)) > 0;
    }
}
