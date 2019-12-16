package org.nutz.spring.boot.service.entity;

import org.nutz.dao.entity.annotation.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
public class IdEntity extends Entity {

    private static final long serialVersionUID = 1L;

    @Id
    long id;

}
