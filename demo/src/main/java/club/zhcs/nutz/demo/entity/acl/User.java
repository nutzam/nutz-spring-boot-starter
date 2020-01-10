package club.zhcs.nutz.demo.entity.acl;

import javax.validation.constraints.NotNull;
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
@Table("t_user")
@Comment("用户")
@EqualsAndHashCode(callSuper = true)
public class User extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @Column("u_name")
    @Comment("用户名")
    @NotNull(message = "用户名不能为空")
    @Size(max = 128, message = "用户名不超过128个字符")
    private String name;

    @Column("u_pwd")
    @Comment("密码")
    @Size(max = 128, message = "密码不超过128个字符")
    private String pwd;

    @Column("u_mobile")
    @Comment("手机号")
    @Size(max = 128, message = "手机号不超过128个字符")
    private String mobile;

}
