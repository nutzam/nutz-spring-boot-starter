package org.nutz.spring.boot.service.entity;

import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author kerbores(kerbores@gmail.com)
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@Deprecated(since = "2.4.0.RELEASE")
public class NamedEntity extends Entity {

    private static final long serialVersionUID = 1L;

    public static final String ID = "id";

    @Name(casesensitive = false)
    @Prev(els = @EL("uuid(64)"))
    String id;

}
