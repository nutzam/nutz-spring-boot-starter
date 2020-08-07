package club.zhcs.nutz.demo.controller;

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
import club.zhcs.nutz.demo.dto.response.ModuleInfo;
import club.zhcs.nutz.demo.dto.response.PermissionInfo;
import club.zhcs.nutz.demo.dto.response.RoleInfo;
import club.zhcs.nutz.demo.entity.acl.User;
import club.zhcs.nutz.demo.service.acl.UserService;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("users")
    public Result<Pagination<User>> search(
                                           @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                           @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                           @RequestParam(value = "key", required = false) String key) {
        return Result.success(userService.searchByKeyAndPage(
                                                             Optional.ofNullable(key).orElse(""),
                                                             page,
                                                             pageSize,
                                                             "name",
                                                             "mobile")
                                         .addParam("key", key));
    }

    @GetMapping("user/{id}/grant/info")
    public Result<List<ModuleInfo>> grantInfo(@PathVariable("id") long id,
                                              @RequestParam(value = "mids", required = false, defaultValue = "-1") Long[] moduleIds) {
        return Result.success(userService.actionInfo(id, moduleIds).getModules());
    }

    @GetMapping("user/{id}/grant/role")
    public Result<List<RoleInfo>> roles(@PathVariable("id") long userId) {
        return Result.success(userService.rolePoweredInfoByUserId(userId));
    }

    @PostMapping("user/{id}/grant/role")
    public Result<Void> grantRoles(@PathVariable("id") long id, @RequestBody List<Long> roles) {
        return userService.grantRole(id, roles) ? Result.success() : Result.fail("设置角色失败");
    }

    @PostMapping("user/{id}/grant")
    public Result<Void> grant(@PathVariable("id") long id,
                        @RequestBody List<String> actionInfos) {
        return userService.grant(id, actionInfos) ? Result.success() : Result.fail("授权失败");
    }

    @GetMapping("user/{id}/permissions")
    public Result<PermissionInfo> permissions(@PathVariable("id") long id) {
        return Result.success(userService.permissionInfo(id));
    }

    @GetMapping("user/{id}")
    public Result<User> get(@PathVariable("id") long id) {
        return Result.success(userService.fetch(id));
    }

    @PostMapping("user")
    public Result<User> add(@Validated @RequestBody User user) {
        user.setPwd(PasswordUtils.encode(user.getPwd(), user.getName()));
        return Result.success(userService.save(user));
    }

    @PutMapping("user")
    public Result<Void> edit(@Validated @RequestBody User user) {
        return userService.update(user, "name", "mobile") ? Result.success() : Result.fail("更新用户失败");
    }

    @DeleteMapping("user/{id}")
    public Result<Void> delete(@PathVariable("id") long id) {
        return userService.delete(id) == 1 ? Result.success() : Result.fail("删除用户失败");
    }

}
