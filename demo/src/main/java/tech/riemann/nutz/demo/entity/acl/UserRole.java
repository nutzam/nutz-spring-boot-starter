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
 * 用户角色
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
@Table("t_acl_user_role")
@Comment("用户角色")
@Schema(name = "UserRole", description = "用户角色")
public class UserRole extends IdBaseEntity{

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名", required = true)
    @Column("ur_user_name")
    @Comment("用户名")
    private String userName;

    @Schema(description = "角色Key", required = true)
    @Column("ur_role_key")
    @Comment("角色Key")
    private String roleKey;
}