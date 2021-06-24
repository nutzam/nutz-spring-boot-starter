package club.zhcs.nutz.demo.dto.response;

import org.nutz.dao.entity.annotation.Column;

import club.zhcs.nutz.demo.entity.acl.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleInfo extends Role {
    private static final long serialVersionUID = 1L;
    @Column("selected")
    @ApiModelProperty("是否选中标识")
    boolean selected;
}
