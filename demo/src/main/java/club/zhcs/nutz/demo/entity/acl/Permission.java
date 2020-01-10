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
@Table("t_permission")
@Comment("权限")
@EqualsAndHashCode(callSuper = true)
public class Permission extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @Column("p_key")
    @Comment("权限唯一键")
    @NotNull(message = "权限唯一键不能为空")
    @Size(max = 128, message = "权限唯一键不超过128个字符")
    private String key;

    @Column("p_name")
    @Comment("权限名称")
    @Size(max = 128, message = "权限名称不超过128个字符")
    private String name;

    @Column("p_descr")
    @Comment("权限描述")
    @Size(max = 255, message = "权限描述不超过255个字符")
    private String descr;

}
