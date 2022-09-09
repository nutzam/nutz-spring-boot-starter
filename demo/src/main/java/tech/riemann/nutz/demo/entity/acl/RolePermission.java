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
 * 角色权限
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
@Table("t_acl_role_permission")
@Comment("角色权限")
@Schema(name = "RolePermission", description = "角色权限")
public class RolePermission extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色key", required = false)
    @Column("rp_role_key")
    @Comment("角色key")
    private String roleKey;

    @Schema(description = "菜单key", required = false)
    @Column("rp_menu_key")
    @Comment("菜单key")
    private String menuKey;

    @Schema(description = "操作按钮key", required = false)
    @Column("rp_button_key")
    @Comment("操作按钮key")
    private String buttonKey;

    @Schema(description = "创建人", required = false)
    @Column("created_by")
    @Comment("创建人")
    private String createdBy;

    @Schema(description = "更新人", required = false)
    @Column("updated_by")
    @Comment("更新人")
    private String updatedBy;
}