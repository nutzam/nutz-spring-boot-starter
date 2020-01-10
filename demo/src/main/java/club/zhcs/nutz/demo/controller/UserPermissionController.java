package club.zhcs.nutz.demo.controller;

import java.util.Optional;

import org.nutz.spring.boot.service.entity.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import club.zhcs.Result;
import club.zhcs.nutz.demo.entity.acl.UserPermission;
import club.zhcs.nutz.demo.service.UserPermissionService;

/**
 * @author mdp 代码生成器
 */
@RestController
public class UserPermissionController {

    @Autowired
    UserPermissionService userPermissionService;

    @GetMapping("/user/permissions")
    public Result<Pager<UserPermission>> search(
                                                @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                                @RequestParam(value = "key", required = false) String key) {
        return Result.success(userPermissionService
                                                   .searchByKeyAndPage(Optional.ofNullable(key)
                                                                               .orElse(""),
                                                                       page,
                                                                       pageSize,
                                                                       "name",
                                                                       "email",
                                                                       "mobile")
                                                   .addParam("key", key));
        // TODO 确定查询字段
    }

    @GetMapping("/user/permission/{id}")
    public Result<UserPermission> get(@PathVariable("id") long id) {
        return Result.success(userPermissionService.fetch(id));
    }

    @PatchMapping("/user/permission")
    public Result<UserPermission> addOrEdit(@Validated @RequestBody UserPermission userPermission) {
        if (userPermission.getId() > 0) {
            // TODO 确定更新字段
            return userPermissionService.update(userPermission, "name", "email", "mobile") ? Result.success(userPermission)
                                                                                           : Result.fail("更新用户权限关系失败");
        }
        return Result.success(userPermissionService.save(userPermission));
    }

    @DeleteMapping("/user/permission/{id}")
    public Result delete(@PathVariable("id") long id) {
        return userPermissionService.delete(id) == 1 ? Result.success() : Result.fail("删除用户权限关系失败");
    }

}
