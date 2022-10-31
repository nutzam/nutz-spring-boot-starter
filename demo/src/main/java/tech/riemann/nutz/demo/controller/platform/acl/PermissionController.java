package tech.riemann.nutz.demo.controller.platform.acl;

import java.util.Arrays;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.spring.boot.service.interfaces.EntityService;
import org.springframework.http.HttpStatus;
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
import tech.riemann.nutz.demo.entity.acl.Permission;
import tech.riemann.nutz.demo.service.acl.PermissionService;
import tech.riemann.nutz.demo.utils.Tree;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 */
/**
 * 菜单 前端控制器
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-13 09:49:03
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Permission", description = "权限")
public class PermissionController {

    private final PermissionService permissionService;

    /**
     * 权限类型码本
     *
     * @return
     */
    @GetMapping("permission/types")
    @Operation(summary = "权限类型")
    public List<Codebook> types() {
        return Arrays.stream(Permission.Type.values())
                     .map(ICodeBook::build)
                     .toList();
    }

    @GetMapping("permissions/tree")
    @Operation(summary = "权限树")
    public List<Tree<String>> permissionTree(
                                             @Parameter(description = "强制拉取") @RequestParam(value = "force",
                                                     required = false) boolean force) {
        return permissionService.permissionTree(force);
    }

    @GetMapping("permissions")
    @Operation(summary = "权限列表")
    public List<Permission> permissions(
                                        @Parameter(description = "强制拉取") @RequestParam(value = "force", required = false) boolean force) {
        return permissionService.permissions(force);
    }

    /**
     * 权限详情
     *
     * @param id
     *            权限id
     * @return 权限
     */
    @GetMapping("permission/{id}")
    @Operation(summary = "权限详情")
    public Permission getPermission(@Parameter(description = "权限id", required = true) @PathVariable("id") long id) {
        return permissionService.fetch(id);
    }

    /**
     * 添加权限
     *
     * @param permission
     *            权限数据
     * @return 权限
     */
    @PutMapping("permission")
    @Operation(summary = "增加权限")
    public Permission savePermission(@Parameter(description = "增加权限") @RequestBody Permission permission) {
        return permissionService.insert(permission);
    }

    /**
     * 添加权限
     *
     * @param permission
     *            权限数据
     * @return 权限
     */
    @PatchMapping("permission")
    @Operation(summary = "编辑权限")
    @ResponseStatus(value = HttpStatus.OK)
    public void updatePermission(@Parameter(description = "增加权限") @RequestBody Permission permission) {
        permissionService.update(permission, Permission.Fields.name, Permission.Fields.description);
    }

    /**
     * 删除权限
     *
     * @param id
     *            权限id
     * @return 是否删除成功
     */
    @DeleteMapping("permission/{key}")
    @Operation(summary = "删除权限")
    @ResponseStatus(value = HttpStatus.OK)
    public void deletePermission(@Parameter(description = "权限key", required = true) @PathVariable("key") String key) {
        permissionService.clear(Cnd.where(Permission::getKey, EntityService.EQ, key));
    }

    @PostMapping("batch-init-permissions")
    @Operation(summary = "批量新增权限")
    @ResponseStatus(value = HttpStatus.OK)
    public void batchInitPermissions(
                                     @RequestBody List<Permission> permissions) {
        permissionService.batchAddPermissions(permissions);
    }

    @PatchMapping("batch-sync-permissions")
    @Operation(summary = "增量新增权限")
    @ResponseStatus(value = HttpStatus.OK)
    public void batchSyncPermissions(@RequestBody List<Permission> permissions) {
        permissionService.updatePermissions(permissions);
    }
}
