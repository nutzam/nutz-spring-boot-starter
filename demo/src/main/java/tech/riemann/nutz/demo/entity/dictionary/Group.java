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
 * 码本分组
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-13 12:25:23
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("t_dictionary_group")
@Comment("码本分组")
@Schema(name = "Group", description = "码本分组")
public class Group extends IdBaseEntity{

    private static final long serialVersionUID = 1L;

    @Schema(description = "分组唯一键", required = true)
    @Column("g_key")
    @Comment("分组唯一键")
    private String key;

    @Schema(description = "分组名称", required = true)
    @Column("g_name")
    @Comment("分组名称")
    private String name;

    @Schema(description = "分组描述", required = false)
    @Column("g_description")
    @Comment("分组描述")
    private String description;

    @Schema(description = "禁用标识", required = true)
    @Column("g_disabled")
    @Comment("禁用标识")
    private Boolean disabled;
}