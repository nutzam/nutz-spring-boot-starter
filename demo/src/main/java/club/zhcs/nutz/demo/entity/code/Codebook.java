package club.zhcs.nutz.demo.entity.code;

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
@Table("t_codebook")
@Comment("码本数据")
@EqualsAndHashCode(callSuper = true)
public class Codebook extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @Column("c_group_id")
    @Comment("分组Id")
    private long groupId;

    @Column("c_parent_id")
    @Comment("上级Id")
    private long parentId;

    @Column("c_index")
    @Comment("序号")
    private long index;

    @Column("c_key")
    @Comment("key")
    @Size(max = 128, message = "key不超过128个字符")
    private String key;

    @Column("c_value")
    @Comment("value")
    @Size(max = 128, message = "value不超过128个字符")
    private String value;

    @Column("c_description")
    @Comment("描述")
    @Size(max = 255, message = "描述不超过255个字符")
    private String description;

    @Column("c_disabled")
    @Comment("禁用标识")
    private boolean disabled;

}
