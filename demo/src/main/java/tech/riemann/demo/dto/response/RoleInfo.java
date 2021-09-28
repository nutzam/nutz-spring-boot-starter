package tech.riemann.demo.dto.response;

import org.nutz.dao.entity.annotation.Column;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tech.riemann.demo.entity.acl.Role;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RoleInfo", description = "角色信息")
public class RoleInfo extends Role {
    private static final long serialVersionUID = 1L;
    @Column("selected")
    @ApiModelProperty("是否选中标识")
    boolean selected;
}
