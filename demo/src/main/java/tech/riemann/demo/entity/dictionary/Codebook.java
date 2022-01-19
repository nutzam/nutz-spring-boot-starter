package tech.riemann.demo.entity.dictionary;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Index;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.TableIndexes;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import tech.riemann.demo.entity.DemoEntity;

/**
 * <p>
 * 码本数据
 * </p>
 *
 * @author Kerbores
 * @since 2021-08-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Accessors(chain = true)
@Table("t_codebook")
@TableIndexes(value = {@Index(unique = true, fields = {"key", "groupId"})})
@Comment("码本数据")
@Schema(name = "Codebook", description = "码本数据")
public class Codebook extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "分组Id", required = true)
    @Column("c_group_id")
    @Comment("分组Id")
    private Long groupId;

    @Schema(description = "上级Key")
    @Column("c_parent_key")
    @Comment("上级Id")
    private String parentKey;

    @Schema(description = "序号")
    @Column("c_index")
    @Comment("序号")
    private Long index;

    @Schema(description = "key", required = true)
    @Column("c_key")
    @Comment("key")
    private String key;

    @Schema(description = "value", required = true)
    @Column("c_value")
    @Comment("value")
    private String value;

    @Schema(description = "描述")
    @Column("c_description")
    @Comment("描述")
    private String description;

    @Schema(description = "禁用标识", required = true)
    @Column("c_disabled")
    @Comment("禁用标识")
    private Boolean disabled;

    public static final String C_GROUP_ID = "c_group_id";

    public static final String C_PARENT_ID = "c_parent_id";

    public static final String C_INDEX = "c_index";

    public static final String C_KEY = "c_key";

    public static final String C_VALUE = "c_value";

    public static final String C_DESCRIPTION = "c_description";

    public static final String C_DISABLED = "c_disabled";

}
