package tech.riemann.demo.entity.acl;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * 角色
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
@Table("t_role")
@Comment("角色")
@ApiModel(value = "Role", description = "角色")
public class Role extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色唯一键", required = true)
    @Column("r_key")
    @Comment("角色唯一键")
    @Name
    private String key;

    @ApiModelProperty(value = "角色名称", required = true)
    @Column("r_name")
    @Comment("角色名称")
    private String name;

    @ApiModelProperty(value = "角色描述")
    @Column("r_description")
    @Comment("角色描述")
    private String description;

    public static final String R_KEY = "r_key";

    public static final String R_NAME = "r_name";

    public static final String R_DESCR = "r_descr";

}
