package club.zhcs.nutz.demo.entity.acl;

import javax.validation.constraints.Size;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

import club.zhcs.nutz.demo.entity.DemoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author mdp 代码生成器
 *
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table("t_user_permission")
@Comment("用户权限关系")
@EqualsAndHashCode(callSuper = true)
public class UserPermission extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @Column("up_action_key")
    @Comment("动作key")
    @Size(max = 50, message = "动作key不超过50个字符")
    private String actionKey;

    @Column("up_module_id")
    @Comment("模块id")
    private long moduleId;

    @Column("up_module_key")
    @Comment("模块key")
    @Size(max = 50, message = "模块key不超过50个字符")
    private String moduleKey;

    @Column("up_user_id")
    @Comment("用户id")
    private long userId;

}
