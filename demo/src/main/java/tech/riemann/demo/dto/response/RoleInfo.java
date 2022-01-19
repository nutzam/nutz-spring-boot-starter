package tech.riemann.demo.dto.response;

import org.nutz.dao.entity.annotation.Column;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tech.riemann.demo.entity.acl.Role;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "RoleInfo", description = "角色信息")
public class RoleInfo extends Role {
    private static final long serialVersionUID = 1L;
    @Column("selected")
    @Schema(description = "是否选中标识")
    boolean selected;
}
