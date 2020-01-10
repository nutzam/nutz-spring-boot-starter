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
import club.zhcs.nutz.demo.entity.acl.Role;
import club.zhcs.nutz.demo.service.RoleService;

/**
 * @author mdp 代码生成器
 */
@RestController
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("/roles")
    public Result<Pager<Role>> search(
                                      @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                      @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                      @RequestParam(value = "key", required = false) String key) {
        return Result.success(roleService
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

    @GetMapping("/role/{id}")
    public Result<Role> get(@PathVariable("id") long id) {
        return Result.success(roleService.fetch(id));
    }

    @PatchMapping("/role")
    public Result<Role> addOrEdit(@Validated @RequestBody Role role) {
        if (role.getId() > 0) {
            // TODO 确定更新字段
            return roleService.update(role, "name", "email", "mobile") ? Result.success(role) : Result.fail("更新角色失败");
        }
        return Result.success(roleService.save(role));
    }

    @DeleteMapping("/role/{id}")
    public Result delete(@PathVariable("id") long id) {
        return roleService.delete(id) == 1 ? Result.success() : Result.fail("删除角色失败");
    }

}
