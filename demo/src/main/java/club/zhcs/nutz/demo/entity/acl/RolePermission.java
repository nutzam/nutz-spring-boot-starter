package club.zhcs.nutz.demo.entity.acl;

import javax.validation.constraints.Size;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

import club.zhcs.nutz.demo.entity.DemoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author mdp 代码生成器
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("t_role_permission")
@Comment("角色权限关系表")
@EqualsAndHashCode(callSuper = true)
public class RolePermission extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @Column("rp_action_key")
    @Comment("动作key")
    @Size(max = 50, message = "动作key不超过50个字符")
    private String actionKey;

    @Column("rp_module_id")
    @Comment("模块id")
    private long moduleId;

    @Column("rp_module_key")
    @Comment("模块key")
    @Size(max = 50, message = "模块key不超过50个字符")
    private String moduleKey;

    @Column("rp_role_id")
    @Comment("角色id")
    private long roleId;

}
