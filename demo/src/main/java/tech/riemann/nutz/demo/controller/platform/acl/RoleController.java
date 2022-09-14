package tech.riemann.nutz.demo.controller.platform.acl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.spring.boot.service.entity.Pagination;
import org.nutz.spring.boot.service.interfaces.EntityService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.dto.response.MenuInfo;
import tech.riemann.nutz.demo.dto.response.PermissionInfo;
import tech.riemann.nutz.demo.entity.acl.Role;
import tech.riemann.nutz.demo.exception.BizException;
import tech.riemann.nutz.demo.service.acl.RoleService;

/**
 * 角色 前端控制器
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-13 09:49:03
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Role", description = "角色")
public class RoleController {

    private final RoleService roleService;

    /**
     * 分页查询角色
     * 
     * @param page
     *            页码
     * @param size
     *            分页大小
     * @param key
     *            关键词
     * @return 角色分页数据
     */
    @GetMapping("roles")
    @Operation(summary = "分页查询角色")
    public Pagination<Role> roles(
                                  @Parameter(description = "页码") @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                  @Parameter(description = "页面大小") @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                  @Parameter(description = "搜索关键词") @RequestParam(name = "key", required = false, defaultValue = "") String key) {
        return roleService.searchByKeyAndPage(key,
                                              page,
                                              size,
                                              Role.Fields.name,
                                              Role.Fields.key,
                                              Role.Fields.description);
    }

    /**
     * 角色详情
     * 
     * @param id
     *            角色id
     * @return 角色
     */
    @GetMapping("role/{id}")
    @Operation(summary = "角色详情")
    public Role roleDetail(@Parameter(description = "角色id", required = true) @PathVariable("id") long id) {
        return roleService.fetch(id);
    }

    /**
     * 添加或者更新角色
     * 
     * @param role
     *            角色数据
     * @return 角色
     */
    @PutMapping("role")
    @Operation(summary = "增加/编辑角色")
    public Role saveOrUpdateRole(@Validated @Parameter(description = "角色") @RequestBody Role role) {
        if (role.getId() != null && role.getId() > 0) {
            if (roleService.update(role) == 1) {
                return role;
            } else {
                throw BizException.create("更新角色失败!");
            }
        }
        return roleService.insert(role);
    }

    /**
     * 删除角色
     * 
     * @param id
     *            角色id
     * @return 是否删除成功
     */
    @DeleteMapping("role/{key}")
    @Operation(summary = "删除角色")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRole(@Parameter(description = "角色名称", required = true) @PathVariable("key") String key) {
        if (roleService.clear(Cnd.where(Role::getName, EntityService.EQ, key)) != 1) {
            throw BizException.create("删除角色失败!");
        }
    }

    @GetMapping("role/{key}/permissions")
    @Operation(summary = "查询角色权限")
    public List<MenuInfo> permissions(
                                      @Parameter(description = "角色key") @PathVariable("key") String key) {
        return roleService.permissionsByRoleKey(key);
    }

    @GetMapping("role/{key}/permission-infos")
    @Operation(summary = "查询用于授权的权限信息")
    public List<PermissionInfo> permissionInfos(
                                                @Parameter(description = "角色key") @PathVariable("key") String key) {
        return roleService.permissionInfosByKey(key);
    }

    @PostMapping("role/{key}/permissions")
    @Operation(summary = "为指定角色授权")
    @ResponseStatus(HttpStatus.OK)
    public void grant(
                      @Parameter(description = "角色key") @PathVariable("key") String key,
                      @Parameter(name = "permissions", description = "待授权信息['menuKey', 'menuKey.buttonKey']") @RequestBody List<String> permissions) {
        if (!roleService.grant(key, permissions)) {
            throw BizException.create("授权失败");
        }
    }

}
