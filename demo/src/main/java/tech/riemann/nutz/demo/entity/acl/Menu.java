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
import tech.riemann.nutz.demo.entity.BaseEntity;

/**
 * 菜单
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
@Table("t_acl_menu")
@Comment("菜单")
@Schema(name = "Menu", description = "菜单")
public class Menu extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @Schema(description = "资源key,英文,用来做业务", required = false)
    @Column("m_key")
    @Comment("资源key,英文,用来做业务")
    private String key;

    @Schema(description = "资源名称,中文用来做标识", required = false)
    @Column("m_name")
    @Comment("资源名称,中文用来做标识")
    private String name;

    @Schema(description = "资源描述", required = false)
    @Column("m_description")
    @Comment("资源描述")
    private String description;

    @Schema(description = "上级菜单key", required = false)
    @Column("m_parent_key")
    @Comment("上级菜单key")
    private String parentKey;

    @Schema(description = "上级菜单key", required = false)
    @Column("m_parent_kay")
    @Comment("上级菜单key")
    private String parentKay;

    @Schema(description = "创建人", required = false)
    @Column("created_by")
    @Comment("创建人")
    private String createdBy;

    @Schema(description = "更新人", required = false)
    @Column("updated_by")
    @Comment("更新人")
    private String updatedBy;
}