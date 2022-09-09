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
import tech.riemann.nutz.demo.entity.IdBaseEntity;

/**
 * 用户权限
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
@Table("t_acl_user_permission")
@Comment("用户权限")
@Schema(name = "UserPermission", description = "用户权限")
public class UserPermission extends IdBaseEntity{

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名", required = true)
    @Column("up_user_name")
    @Comment("用户名")
    private String userName;

    @Schema(description = "菜单key", required = true)
    @Column("up_menu_key")
    @Comment("菜单key")
    private String menuKey;

    @Schema(description = "操作按钮key", required = true)
    @Column("up_button_key")
    @Comment("操作按钮key")
    private String buttonKey;
}