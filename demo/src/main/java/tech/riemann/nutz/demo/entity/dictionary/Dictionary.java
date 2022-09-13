package tech.riemann.nutz.demo.entity.dictionary;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import tech.riemann.nutz.demo.entity.IdBaseEntity;

/**
 * 码本数据
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-13 09:49:04
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("t_dictionary_dictionary")
@Comment("码本数据")
@Schema(name = "Dictionary", description = "码本数据")
public class Dictionary extends IdBaseEntity{

    private static final long serialVersionUID = 1L;

    @Schema(description = "分组Key", required = false)
    @Column("d_group_key")
    @Comment("分组Key")
    private String groupKey;

    @Schema(description = "上级Key", required = false)
    @Column("d_parent_key")
    @Comment("上级Key")
    private String parentKey;

    @Schema(description = "序号", required = false)
    @Column("d_index")
    @Comment("序号")
    private Long index;

    @Schema(description = "key", required = false)
    @Column("d_key")
    @Comment("key")
    private String key;

    @Schema(description = "value", required = false)
    @Column("d_value")
    @Comment("value")
    private String value;

    @Schema(description = "描述", required = false)
    @Column("d_description")
    @Comment("描述")
    private String description;

    @Schema(description = "禁用标识", required = false)
    @Column("d_disabled")
    @Comment("禁用标识")
    private Boolean disabled;
}