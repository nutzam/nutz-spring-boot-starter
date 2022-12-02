package tech.riemann.nutz.demo.dto.response;

import java.util.List;

import org.nutz.json.Json;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import tech.riemann.nutz.demo.config.auth.JwtTokenUtil;
import tech.riemann.nutz.demo.config.auth.JwtUser;
import tech.riemann.nutz.demo.entity.acl.User;

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
@Schema(name = "LoginUser", description = "已登录用户")
public class LoginUser extends User {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色列表", required = true)
    private List<String> roles;

    @Schema(description = "权限列表", required = true)
    private List<String> permissions;

    @Schema(description = "token", required = true)
    String token;

    @Schema(description = "refreshToken", required = true)
    String refreshToken;

    public static LoginUser from(@NotNull JwtUser jwtUser) {
        return Json.fromJson(LoginUser.class, Json.toJson(jwtUser.getUser()))
                   .setRoles(jwtUser.getRoles())
                   .setPermissions(jwtUser.getPermissions())
                   .setToken(JwtTokenUtil.generateToken(jwtUser.getUsername(), jwtUser.getRoles(), jwtUser.getPermissions()))
                   .setRefreshToken(JwtTokenUtil.generateRefreshToken(jwtUser.getUsername()));
    }

}
