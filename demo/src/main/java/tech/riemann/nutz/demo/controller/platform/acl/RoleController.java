package tech.riemann.nutz.demo.controller.platform.acl;

import org.nutz.lang.Lang;
import org.nutz.spring.boot.service.entity.Pagination;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.entity.acl.Role;
import tech.riemann.nutz.demo.service.acl.RoleService;
/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-09
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
                                                Role.Fields.name); 
    }

    /**
     * 角色详情
     * 
     * @param id
     *            角色id
     * @return 角色
     */
    @GetMapping("role/{id}")
    	@Operation(summary ="角色详情")
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
      public Role saveOrUpdateRole(@Validated @Parameter(description ="角色")@RequestBody Role role) {
        if (role.getId() != null && role.getId() > 0) {
            if (roleService.update(role) == 1) {
                return role;
            } else {
                throw Lang.makeThrow("更新角色失败!");
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
    @DeleteMapping("role/{id}")
    @Operation(summary = "删除角色")
      public void deleteRole(@Parameter(description = "角色id", required = true)@PathVariable("id") long id) {
         if(roleService.delete(id) != 1){
         		throw Lang.makeThrow("删除角色失败!");
         }
    }

}
