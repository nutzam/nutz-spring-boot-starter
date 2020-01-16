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
@Table("t_action")
@Comment("功能动作")
@EqualsAndHashCode(callSuper = true)
public class Action extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @Column("a_key")
    @Comment("动作key")
    @NotNull(message = "动作key不能为空")
    @Size(max = 128, message = "动作key不超过128个字符")
    private String key;

    @Column("a_installed")
    @Comment("是否内置标识")
    private boolean installed;

    @Column("a_module_id")
    @Comment("归属的模块id")
    private long moduleId;

    @Column("a_name")
    @Comment("动作名称")
    @Size(max = 128, message = "动作名称不超过128个字符")
    private String name;

}
