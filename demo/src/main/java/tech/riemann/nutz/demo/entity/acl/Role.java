package tech.riemann.nutz.demo.entity.acl;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import tech.riemann.nutz.demo.entity.IdBaseEntity;

/**
 * 角色
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-10 00:29:20
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("t_acl_role")
@Comment("角色")
@Schema(name = "Role", description = "角色")
public class Role extends IdBaseEntity{

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色key,英文,用来做业务", required = true)
    @Name
    //@Prev(els=@EL("uuid(32)")) 这样就可以生成主键
    @Column("r_key")
    @Comment("角色key,英文,用来做业务")
    private String key;

    @Schema(description = "角色名称,中文用来做标识", required = true)
    @Column("r_name")
    @Comment("角色名称,中文用来做标识")
    private String name;

    @Schema(description = "角色描述", required = false)
    @Column("r_description")
    @Comment("角色描述")
    private String description;
}