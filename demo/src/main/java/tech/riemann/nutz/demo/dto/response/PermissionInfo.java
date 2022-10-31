package tech.riemann.nutz.demo.dto.response;

import org.nutz.dao.entity.annotation.Column;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import tech.riemann.nutz.demo.entity.acl.Permission;

/**
 * @author kerbores(kerbores@riemann.tech)
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Accessors(chain = true)
@Schema(name = "PermissionInfo", description = "权限信息,包含是否选中标识")
public class PermissionInfo extends Permission {

    private static final long serialVersionUID = 1L;
    @Column("selected")
    @Schema(description = "权限是否选中标识", required = true)
    boolean selected;
}
