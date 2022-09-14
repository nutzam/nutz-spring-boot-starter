package tech.riemann.nutz.demo.controller.platform.acl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.nutz.dao.Cnd;
import org.nutz.spring.boot.service.entity.Pagination;
import org.nutz.spring.boot.service.interfaces.EntityService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import club.zhcs.enums.Codebook;
import club.zhcs.enums.ICodeBook;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.dto.response.MenuInfo;
import tech.riemann.nutz.demo.dto.response.PermissionInfo;
import tech.riemann.nutz.demo.dto.response.RoleInfo;
import tech.riemann.nutz.demo.entity.acl.User;
import tech.riemann.nutz.demo.entity.acl.User.Sex;
import tech.riemann.nutz.demo.exception.BizException;
import tech.riemann.nutz.demo.service.acl.UserService;

/**
 * 用户 前端控制器
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-13 09:49:03
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "用户")
public class UserController {

    private final UserService userService;

    /**
     * 分页查询用户
     * 
     * @param page
     *            页码
     * @param size
     *            分页大小
     * @param key
     *            关键词
     * @return 用户分页数据
     */
    @GetMapping("users")
    @Operation(summary = "分页查询用户")
    public Pagination<User> users(
                                  @Parameter(description = "页码") @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                  @Parameter(description = "页面大小") @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                  @Parameter(description = "性别") @RequestParam(value = "sex", required = false, defaultValue = "") Sex sex,
                                  @Parameter(description = "搜索关键词") @RequestParam(name = "key", required = false, defaultValue = "") String key) {
        return userService.searchByKeyAndPage(key,
                                              page,
                                              size,
                                              Cnd.NEW().andEX(User::getSex, EntityService.EQ, sex),
                                              User.Fields.name,
                                              User.Fields.fullName,
                                              User.Fields.mobile,
                                              User.Fields.email);
    }

    @GetMapping("user/sexes")
    @Operation(summary = "用户性别")
    public List<Codebook> sexes() {
        return Arrays.stream(Sex.values()).map(ICodeBook::build).collect(Collectors.toList());
    }

    @PatchMapping("user/{name}/password")
    @Operation(summary = "重置密码")
    public String resetPassword(@Parameter(description = "用户名", required = true) @PathVariable("name") String name) {
        return userService.resetPassword(name);
    }

    /**
     * 用户详情
     * 
     * @param id
     *            用户id
     * @return 用户
     */
    @GetMapping("user/{id}")
    @Operation(summary = "用户详情")
    public User userDetail(@Parameter(description = "用户id", required = true) @PathVariable("id") long id) {
        return userService.fetch(id);
    }

    /**
     * 添加或者更新用户
     * 
     * @param user
     *            用户数据
     * @return 用户
     */
    @PutMapping("user")
    @Operation(summary = "增加/编辑用户")
    public User saveOrUpdateUser(@Validated @Parameter(description = "用户") @RequestBody User user) {
        if (user.getId() != null && user.getId() > 0) {
            if (userService.update(user) == 1) {
                return user;
            } else {
                throw BizException.create("更新用户失败!");
            }
        }
        return userService.insert(user);
    }

    /**
     * 删除用户
     * 
     * @param id
     *            用户id
     * @return 是否删除成功
     */
    @DeleteMapping("user/{id}")
    @Operation(summary = "删除用户")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@Parameter(description = "用户id", required = true) @PathVariable("id") long id) {
        if (userService.delete(id) != 1) {
            throw BizException.create("删除用户失败!");
        }
    }

    @GetMapping("user/{name}/permissions")
    @Operation(summary = "查询用户权限")
    public List<MenuInfo> permissions(
                                      @Parameter(description = "用户名") @PathVariable("name") String name) {
        return userService.permissionsByUserName(name);
    }

    @GetMapping("user/{name}/permission-infos")
    @Operation(summary = "查询用于授权的权限信息")
    public List<PermissionInfo> permissionInfos(
                                                @Parameter(description = "用户名") @PathVariable("name") String name) {
        return userService.permissionInfosByUserName(name);
    }

    @PostMapping("user/{name}/permissions")
    @Operation(summary = "为指定用户授权")
    @ResponseStatus(HttpStatus.OK)
    public void grant(
                      @Parameter(description = "用户名") @PathVariable("name") String name,
                      @Parameter(name = "permissions", description = "待授权信息['menuKey', 'menuKey.buttonKey']") @RequestBody List<String> permissions) {
        if (!userService.grant(name, permissions)) {
            throw BizException.create("授权失败");
        }
    }

    @GetMapping("user/{name}/role-infos")
    @Operation(summary = "查询用户权限")
    public List<RoleInfo> roleInfos(
                                    @Parameter(description = "用户名") @PathVariable("name") String name) {
        return userService.roleInfosByUserName(name);
    }

    @PostMapping("user/{name}/roles")
    @Operation(summary = "为指定用户设置角色")
    @ResponseStatus(HttpStatus.OK)
    public void grantRole(
                          @Parameter(description = "用户名") @PathVariable("name") String name,
                          @Parameter(name = "roles", description = "待授权角色") @RequestBody List<String> roles) {
        if (!userService.grantRole(name, roles)) {
            throw BizException.create("授权角色失败");
        }
    }
}
