package tech.riemann.nutz.demo.controller.platform.acl;

import org.nutz.lang.Lang;
import org.nutz.spring.boot.service.entity.Pagination;
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
import tech.riemann.nutz.demo.service.acl.ButtonService;
/**
 * <p>
 * 操作按钮 前端控制器
 * </p>
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-09 23:34:34
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Button", description = "操作按钮")
public class ButtonController {
	
    private final ButtonService buttonService;

    /**
     * 分页查询操作按钮
     * 
     * @param page
     *            页码
     * @param size
     *            分页大小
     * @param key
     *            关键词
     * @return 操作按钮分页数据
     */
    @GetMapping("buttons")
    @Operation(summary = "分页查询操作按钮")
      public Pagination<Button> buttons(
      								 @Parameter(description = "页码") @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                     @Parameter(description = "页面大小") @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                     @Parameter(description = "搜索关键词") @RequestParam(name = "key", required = false, defaultValue = "") String key) {
        return buttonService.searchByKeyAndPage(key,
                                                page,
                                                size,
                                                Button.Fields.name); 
    }

    /**
     * 操作按钮详情
     * 
     * @param id
     *            操作按钮id
     * @return 操作按钮
     */
    @GetMapping("button/{id}")
    	@Operation(summary ="操作按钮详情")
		public Button buttonDetail(@Parameter(description = "操作按钮id", required = true) @PathVariable("id") long id) {
        return buttonService.fetch(id);
    }

    /**
     * 添加或者更新操作按钮
     * 
     * @param button
     *            操作按钮数据
     * @return 操作按钮
     */
    @PutMapping("button")
    @Operation(summary = "增加/编辑操作按钮")
    public Button saveOrUpdateButton(@Validated @Parameter(description ="操作按钮")@RequestBody Button button) {
        if (button.getId() != null && button.getId() > 0) {
            if (buttonService.update(button) == 1) {
                return button;
            } else {
                throw Lang.makeThrow("更新操作按钮失败!");
            }
        }
        return buttonService.insert(button);
    }

    /**
     * 删除操作按钮
     * 
     * @param id
     *            操作按钮id
     * @return 是否删除成功
     */
    @DeleteMapping("button/{id}")
    @Operation(summary = "删除操作按钮")
    @ResponseStatus(HttpStatus.OK)
    public void deleteButton(@Parameter(description = "操作按钮id", required = true)@PathVariable("id") long id) {
         if(buttonService.delete(id) != 1){
         		throw Lang.makeThrow("删除操作按钮失败!");
         }
    }

}
