package tech.riemann.nutz.demo.entity.acl;

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
import tech.riemann.nutz.demo.entity.BaseEntity;

/**
 * 操作按钮
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-09
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("t_acl_button")
@Comment("操作按钮")
@Schema(name = "Button", description = "操作按钮")
public class Button extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @Schema(description = "操作key,英文,用来做业务", required = true)
    @Name
    @Column("b_key")
    @Comment("操作key,英文,用来做业务")
    private String key;

    @Schema(description = "操作名称,中文用来做标识", required = true)
    @Column("b_name")
    @Comment("操作名称,中文用来做标识")
    private String name;

    @Schema(description = "资源描述", required = false)
    @Column("b_description")
    @Comment("资源描述")
    private String description;

    @Schema(description = "归属资源key,如果内置为空", required = true)
    @Column("b_menu_key")
    @Comment("归属资源key,如果内置为空")
    private String menuKey;

    @Schema(description = "是否内置,内置的没有资源归属", required = true)
    @Column("b_installed")
    @Comment("是否内置,内置的没有资源归属")
    private Boolean installed;

    @Schema(description = "创建人", required = false)
    @Column("created_by")
    @Comment("创建人")
    private String createdBy;

    @Schema(description = "更新人", required = false)
    @Column("updated_by")
    @Comment("更新人")
    private String updatedBy;
}