package tech.riemann.demo.entity.acl;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * 功能动作
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
@Table("t_action")
@Comment("功能动作")
@ApiModel(value = "Action", description = "功能动作")
public class Action extends DemoEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "动作key", required = true)
    @Column("a_key")
    @Comment("动作key")
    @Name
    private String key;

    @ApiModelProperty(value = "是否内置标识", required = true)
    @Column("a_installed")
    @Comment("是否内置标识")
    @Default
    private Boolean installed = false;

    @ApiModelProperty(value = "归属的模块id", required = true)
    @Column("a_module_id")
    @Comment("归属的模块id")
    private Long moduleId;

    @ApiModelProperty(value = "动作名称", required = true)
    @Column("a_name")
    @Comment("动作名称")
    private String name;

    public static final String A_KEY = "a_key";

    public static final String A_INSTALLED = "a_installed";

    public static final String A_MODULE_ID = "a_module_id";

    public static final String A_NAME = "a_name";

}
