package club.zhcs.nutz.demo.entity.acl;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Name;
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
@Table("t_module")
@Comment("功能模块")
@EqualsAndHashCode(callSuper = true)
public class Module extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @Name
    @Column("m_key")
    @Comment("模块key")
    @NotNull(message = "模块key不能为空")
    @Size(max = 50, message = "模块key不超过50个字符")
    private String key;

    @Column("m_descr")
    @Comment("模块描述")
    @Size(max = 128, message = "模块描述不超过128个字符")
    private String description;

    @Column("m_name")
    @Comment("模块名称")
    @Size(max = 50, message = "模块名称不超过50个字符")
    private String name;

    public Module id(long id) {
        setId(id);
        return this;
    }

}
