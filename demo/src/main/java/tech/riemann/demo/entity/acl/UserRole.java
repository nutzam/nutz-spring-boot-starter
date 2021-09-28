package tech.riemann.demo.entity.acl;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

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
 * 用户角色关系
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
@Table("t_user_role")
@Comment("用户角色关系")
@ApiModel(value = "UserRole", description = "用户角色关系")
public class UserRole extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色 id", required = true)
    @Column("ur_role_id")
    @Comment("角色 id")
    private Long roleId;

    @ApiModelProperty(value = "用户 id", required = true)
    @Column("ur_user_id")
    @Comment("用户 id")
    private Long userId;

    public static final String UR_ROLE_ID = "ur_role_id";

    public static final String UR_USER_ID = "ur_user_id";

}
