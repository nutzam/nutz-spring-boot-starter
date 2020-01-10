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
import club.zhcs.nutz.demo.entity.acl.UserRole;
import club.zhcs.nutz.demo.service.UserRoleService;

/**
 * @author mdp 代码生成器
 */
@RestController
public class UserRoleController {

    @Autowired
    UserRoleService userRoleService;

    @GetMapping("/user/roles")
    public Result<Pager<UserRole>> search(
                                          @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                          @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                          @RequestParam(value = "key", required = false) String key) {
        return Result.success(userRoleService
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

    @GetMapping("/user/role/{id}")
    public Result<UserRole> get(@PathVariable("id") long id) {
        return Result.success(userRoleService.fetch(id));
    }

    @PatchMapping("/user/role")
    public Result<UserRole> addOrEdit(@Validated @RequestBody UserRole userRole) {
        if (userRole.getId() > 0) {
            // TODO 确定更新字段
            return userRoleService.update(userRole, "name", "email", "mobile") ? Result.success(userRole) : Result.fail("更新用户角色关系失败");
        }
        return Result.success(userRoleService.save(userRole));
    }

    @DeleteMapping("/user/role/{id}")
    public Result delete(@PathVariable("id") long id) {
        return userRoleService.delete(id) == 1 ? Result.success() : Result.fail("删除用户角色关系失败");
    }

}
