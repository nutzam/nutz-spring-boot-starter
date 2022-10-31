package tech.riemann.nutz.demo.dto.response;

import org.nutz.json.Json;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tech.riemann.nutz.demo.entity.acl.Permission;
import tech.riemann.nutz.demo.utils.Treeable;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Schema(name = "PermissionTree", description = "权限树")
public class PermissionTree extends Permission implements Treeable<String> {

    private static final long serialVersionUID = 1L;

    public static PermissionTree build(Permission permission) {
        return Json.fromJson(PermissionTree.class, Json.toJson(permission));
    }

}
