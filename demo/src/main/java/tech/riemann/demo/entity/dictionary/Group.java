package tech.riemann.demo.entity.dictionary;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import tech.riemann.demo.entity.DemoEntity;

/**
 * <p>
 * 码本分组
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
@Table("t_group")
@Comment("码本分组")
@Schema(name = "Group", description = "码本分组")
public class Group extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "分组唯一键", required = true)
    @Column("g_key")
    @Comment("分组唯一键")
    private String key;

    @Schema(description = "分组名称", required = true)
    @Column("g_name")
    @Comment("分组名称")
    private String name;

    @Schema(description = "分组描述")
    @Column("g_description")
    @Comment("分组描述")
    private String description;

    @Schema(description = "禁用标识", required = true)
    @Column("g_disabled")
    @Comment("禁用标识")
    @Default
    private Boolean disabled = false;

    public static final String G_KEY = "g_key";

    public static final String G_NAME = "g_name";

    public static final String G_DESCR = "g_descr";

    public static final String G_DISABLED = "g_disabled";

}
