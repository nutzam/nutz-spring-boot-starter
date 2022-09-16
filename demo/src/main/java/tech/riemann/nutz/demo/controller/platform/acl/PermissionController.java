package tech.riemann.nutz.demo.controller.platform.acl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.Exps;
import org.nutz.lang.Strings;
import org.nutz.spring.boot.service.interfaces.EntityService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.entity.acl.Button;
import tech.riemann.nutz.demo.entity.acl.Menu;
import tech.riemann.nutz.demo.exception.BizException;
import tech.riemann.nutz.demo.service.acl.ButtonService;
import tech.riemann.nutz.demo.service.acl.MenuService;

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

    private final MenuService menuService;

    private final ButtonService buttonService;

    @GetMapping("menus")
    @Operation(summary = "查询全部菜单")
    public List<Menu> menus(
                            @Parameter(description = "搜索关键词") @RequestParam(name = "key", required = false, defaultValue = "") String key) {
        if (Strings.isNotBlank(key)) {
            return menuService.list(Cnd.NEW());
        }
        key = String.format("%%%s%%", key);
        return menuService.list(Cnd.where(Menu::getKey, EntityService.LIKE, key)
                                   .or(Menu::getName, EntityService.LIKE, key)
                                   .or(Menu::getDescription, EntityService.LIKE, key));
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
    public Menu saveOrUpdateMenu(@Validated @Parameter(description = "菜单") @RequestBody Menu menu) {
        if (menu.getId() > 0) {
            if (menuService.update(menu) == 1) {
                return menu;
            } else {
                throw BizException.create("更新菜单失败!");
            }
        }
        return menuService.insert(menu);
    }

    @DeleteMapping("menu/{key}")
    @Operation(summary = "删除菜单")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMenu(@Parameter(description = "菜单key", required = true) @PathVariable("key") String key) {
        if (menuService.delete(key) != 1) {
            throw BizException.create("删除菜单失败!");
        }
    }

    @GetMapping("menu/{key}/buttons")
    @Operation(summary = "菜单下的操作按钮列表")
    public List<Button> buttons(
                                @Parameter(description = "菜单key", required = true) @PathVariable("key") String key,
                                @Parameter(description = "搜索关键词") @RequestParam(name = "key", required = false, defaultValue = "") String searchKey) {
        if (Strings.isNotBlank(key)) {
            return buttonService.list(Cnd.where(Button::getMenuKey, EntityService.EQ, key));
        }
        searchKey = String.format("%%%s%%", searchKey);
        return buttonService.list(Cnd.where(Button::getMenuKey, EntityService.EQ, key)
                                     .and(Exps.begin()
                                              .and(Button::getKey, EntityService.LIKE, searchKey)
                                              .or(Button::getName, EntityService.LIKE, searchKey)
                                              .or(Button::getDescription, EntityService.LIKE, searchKey)));
    }

    @PutMapping("button")
    @Operation(summary = "增加/编辑操作按钮")
    public Button saveOrUpdateButton(@Validated @Parameter(description = "操作按钮") @RequestBody Button button) {
        if (button.getId() > 0) {
            if (buttonService.update(button) == 1) {
                return button;
            } else {
                throw BizException.create("更新操作按钮失败!");
            }
        }
        return buttonService.insert(button);
    }

    @DeleteMapping("menu/{key}button/{buttonKey}")
    @Operation(summary = "删除操作按钮")
    @ResponseStatus(HttpStatus.OK)
    public void deleteButton(
                             @Parameter(description = "菜单key", required = true) @PathVariable("key") String key,
                             @Parameter(description = "操作按钮buttonKey", required = true) @PathVariable("buttonKey") String buttonKey) {
        if (buttonService.clear(
                                Cnd.where(Button::getKey, EntityService.EQ, buttonKey)
                                   .and(Button::getMenuKey, EntityService.EQ, key)) != 1) {
            throw BizException.create("删除操作按钮失败!");
        }
    }

}
