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
 * 角色权限关系表
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
@Table("t_role_permission")
@Comment("角色权限关系表")
@ApiModel(value = "RolePermission", description = "角色权限关系表")
public class RolePermission extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "动作key", required = true)
    @Column("rp_action_key")
    @Comment("动作key")
    private String actionKey;

    @ApiModelProperty(value = "模块id", required = true)
    @Column("rp_module_id")
    @Comment("模块id")
    private Long moduleId;

    @ApiModelProperty(value = "模块key", required = true)
    @Column("rp_module_key")
    @Comment("模块key")
    private String moduleKey;

    @ApiModelProperty(value = "角色id", required = true)
    @Column("rp_role_id")
    @Comment("角色id")
    private Long roleId;

    public static final String RP_ACTION_KEY = "rp_action_key";

    public static final String RP_MODULE_ID = "rp_module_id";

    public static final String RP_MODULE_KEY = "rp_module_key";

    public static final String RP_ROLE_ID = "rp_role_id";

}
