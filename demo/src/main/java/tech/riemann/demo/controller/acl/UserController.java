package tech.riemann.demo.controller.acl;

import java.util.List;
import java.util.Optional;

import org.nutz.spring.boot.service.entity.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import club.zhcs.Result;
import club.zhcs.auth.PasswordUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import tech.riemann.demo.dto.response.ModuleInfo;
import tech.riemann.demo.dto.response.PermissionInfo;
import tech.riemann.demo.dto.response.RoleInfo;
import tech.riemann.demo.entity.acl.User;
import tech.riemann.demo.service.acl.UserService;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
@RestController
@Tag(name = "User", description = "用户模块")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("users")
    @Operation(summary = "分页查询用户")
    public Result<Pagination<User>> search(
                                           @Parameter(description = "页面") @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                           @Parameter(description = "分页大小") @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                           @Parameter(description = "搜索关键词") @RequestParam(value = "key", required = false) String key) {

        return Result.success(userService.searchByKeyAndPage(
                                                             Optional.ofNullable(key).orElse(""),
                                                             page,
                                                             pageSize,
                                                             User.Fields.name,
                                                             User.Fields.mobile)
                                         .addParam("key", key));
    }

    @GetMapping("user/{id}/grant/info")
    @Operation(summary = "根据id获取指定用户在指定模块下的授权情况")
    public Result<List<ModuleInfo>> grantInfo(
                                              @Parameter(description = "用户id") @PathVariable("id") long id,
                                              @Parameter(description = "系统功能id数组") @RequestParam(value = "mids", required = false, defaultValue = "-1") Long[] moduleIds) {

        return Result.success(userService.actionInfo(id, moduleIds).getModules());
    }

    @GetMapping("user/{id}/grant/role")
    @Operation(summary = "获取指定用户的角色授权")
    public Result<List<RoleInfo>> roles(
                                        @Parameter(description = "用户id") @PathVariable("id") long userId) {

        return Result.success(userService.rolePoweredInfoByUserId(userId));
    }

    @PostMapping("user/{id}/grant/role")
    @Operation(summary = "为指定用户设置角色")
    public Result<Void> grantRoles(
                                   @Parameter(description = "用户id") @PathVariable("id") long id,
                                   @Parameter(description = "角色列表") @RequestBody List<Long> roles) {

        return userService.grantRole(id, roles) ? Result.success() : Result.fail("设置角色失败");
    }

    @PostMapping("user/{id}/grant")
    @Operation(summary = "为指定用户授权")
    public Result<Void> grant(
                              @Parameter(description = "用户id") @PathVariable("id") long id,
                              @Parameter(description = "待授权信息(moduleKey.actionKey)") @RequestBody List<String> actionInfos) {

        return userService.grant(id, actionInfos) ? Result.success() : Result.fail("授权失败");
    }

    @GetMapping("user/{id}/permissions")
    @Operation(summary = "获取指定用户的授权情况")
    public Result<PermissionInfo> permissions(
                                              @Parameter(description = "用户id") @PathVariable("id") long id) {

        return Result.success(userService.permissionInfo(id));
    }

    @GetMapping("user/{id}")
    @Operation(summary = "获取用户详情")
    public Result<User> get(
                            @Parameter(description = "用户id") @PathVariable("id") long id) {

        return Result.success(userService.fetch(id));
    }

    @PostMapping("user")
    @Operation(summary = "创建用户")
    public Result<User> add(
                            @Parameter(description = "用户数据") @Validated @RequestBody User user) {
        user.setPassword(PasswordUtils.randomSaltEncode(user.getPassword()));
        return Result.success(userService.insert(user));
    }

    @PutMapping("user")
    @Operation(summary = "根据id更新用户")
    public Result<Void> edit(
                             @Parameter(description = "用户数据") @Validated @RequestBody User user) {
        return userService.update(user, "name", "mobile") ? Result.success() : Result.fail("更新用户失败");
    }

    @DeleteMapping("user/{id}")
    @Operation(summary = "删除用户")
    public Result<Void> delete(@Parameter(description = "用户id") @PathVariable("id") long id) {
        return userService.delete(id) == 1 ? Result.success() : Result.fail("删除用户失败");
    }

}
