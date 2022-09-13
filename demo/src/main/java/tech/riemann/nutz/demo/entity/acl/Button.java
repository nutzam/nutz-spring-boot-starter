package tech.riemann.nutz.demo.entity.acl;

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
 * 操作按钮
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-13 09:49:03
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
public class Button extends IdBaseEntity{

    private static final long serialVersionUID = 1L;

    @Schema(description = "操作key,英文,用来做业务", required = true)
    @Column("b_key")
    @Comment("操作key,英文,用来做业务")
    private String key;

    @Schema(description = "操作名称,中文用来做标识", required = false)
    @Column("b_name")
    @Comment("操作名称,中文用来做标识")
    private String name;

    @Schema(description = "资源描述", required = false)
    @Column("b_description")
    @Comment("资源描述")
    private String description;

    @Schema(description = "归属资源key,如果内置为空", required = false)
    @Column("b_menu_key")
    @Comment("归属资源key,如果内置为空")
    private String menuKey;

    @Schema(description = "是否内置,内置的没有资源归属", required = false)
    @Column("b_installed")
    @Comment("是否内置,内置的没有资源归属")
    private Boolean installed;
}