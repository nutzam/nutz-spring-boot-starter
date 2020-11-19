package club.zhcs.nutz.demo.controller.acl;

import java.util.List;
import java.util.Optional;

import org.nutz.spring.boot.service.entity.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import club.zhcs.Result;
import club.zhcs.nutz.demo.dto.response.ModuleInfo;
import club.zhcs.nutz.demo.dto.response.PermissionInfo;
import club.zhcs.nutz.demo.entity.acl.Role;
import club.zhcs.nutz.demo.service.acl.RoleService;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@RestController
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("roles")
    public Result<Pagination<Role>> search(
                                           @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                           @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                           @RequestParam(value = "key", required = false) String key) {
        return Result.success(roleService.searchByKeyAndPage(Optional.ofNullable(key)
                                                                     .orElse(""),
                                                             page,
                                                             pageSize,
                                                             "key",
                                                             "name",
                                                             "description")
                                         .addParam("key", key));
    }

    @GetMapping("role/{id}/grant/info")
    public Result<List<ModuleInfo>> grantInfo(@PathVariable("id") long id,
                                              @RequestParam(value = "mids", required = false, defaultValue = "-1") Long[] moduleIds) {
        return Result.success(roleService.actionInfo(id, moduleIds).getModules());
    }

    @PostMapping("role/{id}/grant")
    public Result<Void> grant(@PathVariable("id") long id,
                              @RequestBody List<String> actionInfos) {
        return roleService.grant(id, actionInfos) ? Result.success() : Result.fail("授权失败");
    }

    @GetMapping("role/{id}/permissions")
    public Result<PermissionInfo> permissions(@PathVariable("id") long id) {
        return Result.success(roleService.permissionInfo(id));
    }

    @GetMapping("role/{id}")
    public Result<Role> get(@PathVariable("id") long id) {
        return Result.success(roleService.fetch(id));
    }

    @PostMapping("role")
    public Result<Role> add(@RequestBody Role role) {
        return Result.success(roleService.save(role));
    }

    @PutMapping("role")
    public Result<Void> edit(@RequestBody Role role) {
        return roleService.update(role, "name", "description") ? Result.success() : Result.fail("更新角色失败");
    }

    @DeleteMapping("role/{id}")
    public Result<Void> delete(@PathVariable("id") long id) {
        return roleService.delete(id) == 1 ? Result.success() : Result.fail("删除角色失败");
    }

}
