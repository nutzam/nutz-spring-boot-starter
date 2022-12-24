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
 * @since 2022-09-13 12:25:23
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
public class UserPermission extends IdBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名", required = true)
    @Column("up_user_name")
    @Comment("用户名")
    private String userName;

    @Schema(description = "权限keyPath")
    @Column("up_permission_key_path")
    @Comment("权限keyPath")
    private String permissionKeyPath;
}