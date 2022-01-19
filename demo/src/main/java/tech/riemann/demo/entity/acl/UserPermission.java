package tech.riemann.demo.entity.acl;

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
import tech.riemann.demo.entity.DemoEntity;

/**
 * <p>
 * 用户权限关系
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
@Table("t_user_permission")
@Comment("用户权限关系")
@Schema(name = "UserPermission", description = "用户权限关系")
public class UserPermission extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "动作key", required = true)
    @Column("up_action_key")
    @Comment("动作key")
    private String actionKey;

    @Schema(description = "模块id", required = true)
    @Column("up_module_id")
    @Comment("模块id")
    private Long moduleId;

    @Schema(description = "模块key", required = true)
    @Column("up_module_key")
    @Comment("模块key")
    private String moduleKey;

    @Schema(description = "用户id", required = true)
    @Column("up_user_id")
    @Comment("用户id")
    private Long userId;

    public static final String UP_ACTION_KEY = "up_action_key";

    public static final String UP_MODULE_ID = "up_module_id";

    public static final String UP_MODULE_KEY = "up_module_key";

    public static final String UP_USER_ID = "up_user_id";

}
