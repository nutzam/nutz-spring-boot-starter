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
@Table("t_role")
@Comment("角色")
@EqualsAndHashCode(callSuper = true)
public class Role extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @Column("r_key")
    @Comment("角色唯一键")
    @NotNull(message = "角色唯一键不能为空")
    @Size(max = 128, message = "角色唯一键不超过128个字符")
    private String key;

    @Column("r_name")
    @Comment("角色名称")
    @Size(max = 128, message = "角色名称不超过128个字符")
    private String name;

    @Column("r_descr")
    @Comment("角色描述")
    @Size(max = 255, message = "角色描述不超过255个字符")
    private String description;

}
