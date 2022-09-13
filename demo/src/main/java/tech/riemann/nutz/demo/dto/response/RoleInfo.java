package tech.riemann.nutz.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import tech.riemann.nutz.demo.entity.acl.Role;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Accessors(chain = true)
@Schema(name = "RoleInfo", description = "角色信息,包含是否选中标识")
public class RoleInfo extends Role {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色是否选中标识", required = true)
    boolean selected;

}
