package org.nutz.spring.boot.service.entity;

import org.nutz.dao.entity.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class IdEntity extends Entity {

    private static final long serialVersionUID = 1L;

    public static final String ID_FIELD = "id";

    @Id
    private long id;

}
