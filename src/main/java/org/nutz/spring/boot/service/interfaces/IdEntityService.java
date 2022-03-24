package org.nutz.spring.boot.service.interfaces;

import java.io.Serializable;

import org.nutz.dao.Cnd;
import org.nutz.dao.entity.EntityField;

/**
 * @author wkipy
 *
 */
public interface IdEntityService<T extends Serializable> extends EntityService<T> {

    /**
     * 根据@Id所在的属性的值获取一个实体对象
     * 
     * @param id
     *            属性的值
     * @return 实体对象,如不存在则返回null
     */
    public default T fetch(long id) {
        return dao().fetch(getEntityType(), id);
    }

    /**
     * 根据@Id所在的属性的值删除一个实体对象
     * 
     * @param id
     *            属性的值
     * @return 删除的记录数, 通常是0或者1
     */
    public default int delete(long id) {
        return dao().delete(getEntityType(), id);
    }

    /**
     * 根据@Id所在的属性在数据库中的最大值
     * 
     * @return 最大值,若数据库中没有数据,会抛出空指针异常
     */
    public default int getMaxId() {
        return dao().getMaxId(getEntityType());
    }

    /**
     * 是否存在@Id所在属性的值为指定值的记录
     * 
     * @param id
     *            属性的值
     * @return true,如果存在的话
     */
    public default boolean exists(long id) {
        EntityField ef = getEntity().getIdField();
        if (null == ef)
            return false;
        return dao().count(getEntityType(), Cnd.where(ef.getName(), "=", id)) > 0;
    }
}
