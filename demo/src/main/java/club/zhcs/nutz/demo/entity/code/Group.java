package club.zhcs.nutz.demo.entity.code;

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
@Table("t_group")
@Comment("码本分组")
@EqualsAndHashCode(callSuper = true)
public class Group extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @Column("g_key")
    @Comment("分组唯一键")
    @NotNull(message = "分组唯一键不能为空")
    @Size(max = 128, message = "分组唯一键不超过128个字符")
    private String key;

    @Column("g_name")
    @Comment("分组名称")
    @Size(max = 128, message = "分组名称不超过128个字符")
    private String name;

    @Column("g_descr")
    @Comment("分组描述")
    @Size(max = 255, message = "分组描述不超过255个字符")
    private String description;

    @Column("g_disabled")
    @Comment("禁用标识")
    private boolean disabled;

}
