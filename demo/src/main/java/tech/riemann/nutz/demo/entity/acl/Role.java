package tech.riemann.nutz.demo.entity.acl;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import tech.riemann.nutz.demo.entity.BaseEntity;

/**
 * 角色
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-09
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
public class Role extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色key,英文,用来做业务", required = false)
    @Column("r_key")
    @Comment("角色key,英文,用来做业务")
    private String key;

    @Schema(description = "角色名称,中文用来做标识", required = false)
    @Column("r_name")
    @Comment("角色名称,中文用来做标识")
    private String name;

    @Schema(description = "角色描述", required = false)
    @Column("r_description")
    @Comment("角色描述")
    private String description;

    @Schema(description = "创建人", required = false)
    @Column("created_by")
    @Comment("创建人")
    private String createdBy;

    @Schema(description = "更新人", required = false)
    @Column("updated_by")
    @Comment("更新人")
    private String updatedBy;
}