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
import tech.riemann.nutz.demo.entity.acl.Menu;
import tech.riemann.nutz.demo.service.acl.MenuService;
/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-09
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Menu", description = "菜单")
public class MenuController {
	
    private final MenuService menuService;

    /**
     * 分页查询菜单
     * 
     * @param page
     *            页码
     * @param size
     *            分页大小
     * @param key
     *            关键词
     * @return 菜单分页数据
     */
    @GetMapping("menus")
    @Operation(summary = "分页查询菜单")
      public Pagination<Menu> menus(
      								 @Parameter(description = "页码") @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                     @Parameter(description = "页面大小") @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                     @Parameter(description = "搜索关键词") @RequestParam(name = "key", required = false, defaultValue = "") String key) {
        return menuService.searchByKeyAndPage(key,
                                                page,
                                                size,
                                                Menu.Fields.name); 
    }

    /**
     * 菜单详情
     * 
     * @param id
     *            菜单id
     * @return 菜单
     */
    @GetMapping("menu/{id}")
    	@Operation(summary ="菜单详情")
		public Menu menuDetail(@Parameter(description = "菜单id", required = true) @PathVariable("id") long id) {
        return menuService.fetch(id);
    }

    /**
     * 添加或者更新菜单
     * 
     * @param menu
     *            菜单数据
     * @return 菜单
     */
    @PutMapping("menu")
    @Operation(summary = "增加/编辑菜单")
      public Menu saveOrUpdateMenu(@Validated @Parameter(description ="菜单")@RequestBody Menu menu) {
        if (menu.getId() != null && menu.getId() > 0) {
            if (menuService.update(menu) == 1) {
                return menu;
            } else {
                throw Lang.makeThrow("更新菜单失败!");
            }
        }
        return menuService.insert(menu);
    }

    /**
     * 删除菜单
     * 
     * @param id
     *            菜单id
     * @return 是否删除成功
     */
    @DeleteMapping("menu/{id}")
    @Operation(summary = "删除菜单")
      public void deleteMenu(@Parameter(description = "菜单id", required = true)@PathVariable("id") long id) {
         if(menuService.delete(id) != 1){
         		throw Lang.makeThrow("删除菜单失败!");
         }
    }

}
