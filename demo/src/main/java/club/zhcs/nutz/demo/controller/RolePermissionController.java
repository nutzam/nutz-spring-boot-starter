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
import club.zhcs.nutz.demo.entity.acl.RolePermission;
import club.zhcs.nutz.demo.service.RolePermissionService;

/**
 * @author mdp 代码生成器
 */
@RestController
public class RolePermissionController {

    @Autowired
    RolePermissionService rolePermissionService;

    @GetMapping("/role/permissions")
    public Result<Pager<RolePermission>> search(
                                                @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                                @RequestParam(value = "key", required = false) String key) {
        return Result.success(rolePermissionService
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

    @GetMapping("/role/permission/{id}")
    public Result<RolePermission> get(@PathVariable("id") long id) {
        return Result.success(rolePermissionService.fetch(id));
    }

    @PatchMapping("/role/permission")
    public Result<RolePermission> addOrEdit(@Validated @RequestBody RolePermission rolePermission) {
        if (rolePermission.getId() > 0) {
            // TODO 确定更新字段
            return rolePermissionService.update(rolePermission, "name", "email", "mobile") ? Result.success(rolePermission)
                                                                                           : Result.fail("更新角色权限关系表失败");
        }
        return Result.success(rolePermissionService.save(rolePermission));
    }

    @DeleteMapping("/role/permission/{id}")
    public Result delete(@PathVariable("id") long id) {
        return rolePermissionService.delete(id) == 1 ? Result.success() : Result.fail("删除角色权限关系表失败");
    }

}
