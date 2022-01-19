package tech.riemann.demo.controller.acl;

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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import tech.riemann.demo.dto.response.ModuleInfo;
import tech.riemann.demo.dto.response.PermissionInfo;
import tech.riemann.demo.entity.acl.Role;
import tech.riemann.demo.service.acl.RoleService;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
@RestController
@Schema(name = "Role", description = "角色模块")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("roles")
    @Operation(summary = "分页查询角色")
    public Result<Pagination<Role>> search(
                                           @Parameter(name = "页面") @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                           @Parameter(name = "分页大小") @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                           @Parameter(name = "搜索关键词") @RequestParam(value = "key", required = false) String key) {

        return Result.success(roleService.searchByKeyAndPage(Optional.ofNullable(key)
                                                                     .orElse(""),
                                                             page,
                                                             pageSize,
                                                             Role.Fields.key,
                                                             Role.Fields.name,
                                                             Role.Fields.description)
                                         .addParam("key", key));
    }

    @GetMapping("role/{id}/grant/info")
    @Operation(summary = "根据id获取指定角色下指定模块下的授权情况")
    public Result<List<ModuleInfo>> grantInfo(
                                              @Parameter(name = "角色id") @PathVariable("id") long id,
                                              @Parameter(name = "系统功能id数组") @RequestParam(value = "mids", required = false, defaultValue = "-1") Long[] moduleIds) {

        return Result.success(roleService.actionInfo(id, moduleIds).getModules());
    }

    @PostMapping("role/{id}/grant")
    @Operation(summary = "为指定角色授权")
    public Result<Void> grant(
                              @Parameter(name = "角色id") @PathVariable("id") long id,
                              @Parameter(name = "待授权信息(moduleKey.actionKey)") @RequestBody List<String> actionInfos) {

        return roleService.grant(id, actionInfos) ? Result.success() : Result.fail("授权失败");
    }

    @GetMapping("role/{id}/permissions")
    @Operation(summary = "获取指定角色的授权情况")
    public Result<PermissionInfo> permissions(
                                              @Parameter(name = "角色id") @PathVariable("id") long id) {

        return Result.success(roleService.permissionInfo(id));
    }

    @GetMapping("role/{id}")
    @Operation(summary = "获取角色详情")
    public Result<Role> get(
                            @Parameter(name = "角色id") @PathVariable("id") long id) {

        return Result.success(roleService.fetch(id));
    }

    @PostMapping("role")
    @Operation(summary = "创建角色")
    public Result<Role> add(
                            @Parameter(name = "角色数据") @RequestBody Role role) {

        return Result.success(roleService.save(role));
    }

    @PutMapping("role")
    @Operation(summary = "根据id更新角色")
    public Result<Void> edit(
                             @Parameter(name = "角色数据") @RequestBody Role role) {

        return roleService.update(role, "name", "description") ? Result.success() : Result.fail("更新角色失败");
    }

    @DeleteMapping("role/{id}")
    @Operation(summary = "删除指定角色")
    public Result<Void> delete(
                               @Parameter(name = "角色id") @PathVariable("id") long id) {

        return roleService.delete(id) == 1 ? Result.success() : Result.fail("删除角色失败");
    }

}
