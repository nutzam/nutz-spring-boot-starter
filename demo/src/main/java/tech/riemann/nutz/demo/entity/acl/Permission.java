package tech.riemann.nutz.demo.entity.acl;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.json.JsonField;

import com.fasterxml.jackson.annotation.JsonGetter;

import club.zhcs.enums.Codebook;
import club.zhcs.enums.ICodeBook;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import tech.riemann.nutz.demo.entity.IdBaseEntity;

/**
 * 权限
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-10-31 16:45:15
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("t_acl_permission")
@Comment("权限")
@Schema(name = "Permission", description = "权限")
public class Permission extends IdBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "权限key,英文", required = false)
    @Column("p_key")
    @Comment("权限key,英文")
    private String key;

    @Schema(description = "权限keyPath,用来做业务,(父级keyPath.key)", required = false)
    @Column("p_key_path")
    @Comment("权限keyPath,用来做业务,(父级keyPath.key)")
    private String keyPath;

    @Schema(description = "权限名称,中文用来做标识", required = false)
    @Column("p_name")
    @Comment("权限名称,中文用来做标识")
    private String name;

    @Schema(description = "权限描述", required = false)
    @Column("p_description")
    @Comment("权限描述")
    private String description;

    @Schema(description = "父权限key", required = false)
    @Column("p_parent_key")
    @Comment("父权限key")
    private String parentKey;

    @Schema(description = "权限类型", required = false)
    @Column("p_type")
    @Comment("权限类型")
    @Default
    private Type type = Type.MENU;

    @Getter
    @AllArgsConstructor
    public enum Type implements ICodeBook {
        /**
         * 菜单/页面
         */
        MENU("menu", "菜单"),
        /**
         * 按钮
         */
        BUTTON("button", "按钮"),
        /**
         * 其他页面元素
         */
        OTHER("other", "其他页面元素");

        String code;
        String description;

    }

    @JsonGetter
    @JsonField
    public Codebook getTypeInfo() {
        return type == null ? null : type.build();
    }

    public void setTypeInfo(Codebook typeInfo) {
        setType(Type.valueOf(typeInfo.getName()));
    }
}
