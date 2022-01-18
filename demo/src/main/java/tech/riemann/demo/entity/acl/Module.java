package tech.riemann.demo.entity.acl;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

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
 * 功能模块
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
@Table("t_module")
@Comment("功能模块")
@Schema(name = "Module", description = "功能模块")
public class Module extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "模块key", required = true)
    @Column("m_key")
    @Comment("模块key")
    @Name
    private String key;

    @Schema(description = "模块描述")
    @Column("m_description")
    @Comment("模块描述")
    private String description;

    @Schema(description = "模块名称", required = true)
    @Column("m_name")
    @Comment("模块名称")
    private String name;

    public static final String M_KEY = "m_key";

    public static final String M_DESCR = "m_descr";

    public static final String M_NAME = "m_name";

}
