package tech.riemann.nutz.demo.dto.response;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.nutz.json.Json;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
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

	@Schema(description = "角色列表", requiredMode = RequiredMode.REQUIRED)
	private List<String> roles;

	@Schema(description = "权限列表", requiredMode = RequiredMode.REQUIRED)
	private List<String> permissions;

	@Schema(description = "token", requiredMode = RequiredMode.REQUIRED)
	String token;

	@Schema(description = "refreshToken", requiredMode = RequiredMode.REQUIRED)
	String refreshToken;

	public static LoginUser from(@NotNull JwtUser jwtUser) {
		return Json.fromJson(LoginUser.class, Json.toJson(jwtUser.getUser())).setRoles(jwtUser.getRoles())
				.setPermissions(jwtUser.getPermissions())
				.setToken(
						JwtTokenUtil.generateToken(jwtUser.getUsername(), jwtUser.getRoles(), jwtUser.getPermissions()))
				.setRefreshToken(JwtTokenUtil.generateRefreshToken(jwtUser.getUsername()));
	}

}
