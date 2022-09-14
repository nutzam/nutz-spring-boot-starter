package tech.riemann.nutz.demo.dto.response;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import tech.riemann.nutz.demo.entity.acl.Button;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@Data
@EqualsAndHashCode(callSuper = false, of = {"key", "menuKey"})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Accessors(chain = true)
@Schema(name = "PermissionInfo", description = "权限信息")
public class PermissionInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Schema(description = "权限是否选中标识", required = true)
    @Column("selected")
    boolean selected;

    @Schema(description = "菜单key,英文,用来做业务", required = true)
    @Column("m_key")
    @Comment("菜单key,英文,用来做业务")
    private String menuKey;

    @Schema(description = "菜单名称,中文用来做标识", required = true)
    @Column("m_name")
    @Comment("菜单名称,中文用来做标识")
    private String menuName;

    @Schema(description = "菜单描述")
    @Column("m_description")
    @Comment("菜单描述")
    private String menuDescription;

    @Schema(description = "父菜单key")
    @Column("m_parent_key")
    @Comment("父菜单key")
    private String parentKey;

    @Schema(description = "按钮key,英文,用来做业务", required = true)
    @Size(min = 3, max = 15)
    @Column("b_key")
    @Comment("按钮key,英文,用来做业务")
    private String key;

    @Schema(description = "按钮名称,中文用来做标识", required = true)
    @Size(min = 2, max = 15)
    @Column("b_name")
    @Comment("按钮名称,中文用来做标识")
    private String name;

    @Schema(description = "按钮描述")
    @Column("b_description")
    @Comment("按钮描述")
    private String description;

    @Schema(description = "是否内置,内置的没有菜单归属")
    @Column("b_installed")
    @Comment("是否内置,内置的没有菜单归属")
    private Boolean installed;

    public MenuInfo toMenuInfo() {
        return MenuInfo.builder()
                       .name(getMenuName())
                       .key(getMenuKey())
                       .description(getMenuDescription())
                       .parentKey(getParentKey())
                       .build();
    }

    public Button toButton() {
        return Button.builder()
                     .key(key)
                     .menuKey(menuKey)
                     .name(name)
                     .description(description)
                     .installed(installed)
                     .build();
    }
}
