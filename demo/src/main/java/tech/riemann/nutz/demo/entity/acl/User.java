package tech.riemann.nutz.demo.entity.acl;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

import club.zhcs.auth.AuthUser;
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
 * 用户
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
@Table("t_acl_user")
@Comment("用户")
@Schema(name = "User", description = "用户")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名", required = false)
    @Column("u_name")
    @Comment("用户名")
    private String name;

    @Schema(description = "密码", required = false)
    @Column("u_password")
    @Comment("密码")
    private String password;

    @Schema(description = "手机号", required = false)
    @Column("u_mobile")
    @Comment("手机号")
    private String mobile;

    @Schema(description = "性别", required = false)
    @Column("u_sex")
    @Comment("性别")
    private String sex;

    @Schema(description = "邮箱", required = false)
    @Column("u_email")
    @Comment("邮箱")
    private String email;

    @Schema(description = "真实姓名", required = false)
    @Column("u_full_name")
    @Comment("真实姓名")
    private String fullName;

    @Schema(description = "创建人", required = false)
    @Column("created_by")
    @Comment("创建人")
    private String createdBy;

    @Schema(description = "更新人", required = false)
    @Column("updated_by")
    @Comment("更新人")
    private String updatedBy;

    /**
     * @return
     */
    public AuthUser toUser() {
        // TODO Auto-generated method stub
        return null;
    }
}
