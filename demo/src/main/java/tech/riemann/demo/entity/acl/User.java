package tech.riemann.demo.entity.acl;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import club.zhcs.auth.AuthUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import tech.riemann.demo.entity.DemoEntity;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author Kerbores
 * @since 2021-08-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Accessors(chain = true)
@Table("t_user")
@Comment("用户")
@ApiModel(value = "User", description = "用户")
public class User extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名", required = true)
    @Column("u_name")
    @Comment("用户名")
    @Name
    private String name;

    @ApiModelProperty(value = "密码", required = true)
    @Column("u_password")
    @Comment("密码")
    private String password;

    @ApiModelProperty(value = "手机号", required = true)
    @Column("u_mobile")
    @Comment("手机号")
    private String mobile;

    /**
     * @return
     */
    public AuthUser toUser() {
        return AuthUser.builder()
                       .userName(getName())
                       .password("nutz-demo")
                       .build()
                       .token()
                       .addExt("user", this);
    }

    public static final String U_NAME = "u_name";

    public static final String U_PWD = "u_pwd";

    public static final String U_MOBILE = "u_mobile";

}
