package club.zhcs.nutz.demo.entity.acl;

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
@Table("t_user_role")
@Comment("用户角色关系")
@EqualsAndHashCode(callSuper = true)
public class UserRole extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @Column("ur_role_id")
    @Comment("角色 id")
    private long roleId;

    @Column("ur_user_id")
    @Comment("用户 id")
    private long userId;

}
