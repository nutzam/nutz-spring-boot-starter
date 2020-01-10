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
import club.zhcs.nutz.demo.entity.acl.Permission;
import club.zhcs.nutz.demo.service.PermissionService;

/**
 * @author mdp 代码生成器
 */
@RestController
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @GetMapping("/permissions")
    public Result<Pager<Permission>> search(
                                            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                            @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                            @RequestParam(value = "key", required = false) String key) {
        return Result.success(permissionService
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

    @GetMapping("/permission/{id}")
    public Result<Permission> get(@PathVariable("id") long id) {
        return Result.success(permissionService.fetch(id));
    }

    @PatchMapping("/permission")
    public Result<Permission> addOrEdit(@Validated @RequestBody Permission permission) {
        if (permission.getId() > 0) {
            // TODO 确定更新字段
            return permissionService.update(permission, "name", "email", "mobile") ? Result.success(permission) : Result.fail("更新权限失败");
        }
        return Result.success(permissionService.save(permission));
    }

    @DeleteMapping("/permission/{id}")
    public Result delete(@PathVariable("id") long id) {
        return permissionService.delete(id) == 1 ? Result.success() : Result.fail("删除权限失败");
    }

}
