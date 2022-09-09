package tech.riemann.nutz.demo.controller.platform.dictionary;

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
import tech.riemann.nutz.demo.entity.dictionary.Group;
import tech.riemann.nutz.demo.service.dictionary.GroupService;
/**
 * <p>
 * 码本分组 前端控制器
 * </p>
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-09
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Group", description = "码本分组")
public class GroupController {
	
    private final GroupService groupService;

    /**
     * 分页查询码本分组
     * 
     * @param page
     *            页码
     * @param size
     *            分页大小
     * @param key
     *            关键词
     * @return 码本分组分页数据
     */
    @GetMapping("groups")
    @Operation(summary = "分页查询码本分组")
      public Pagination<Group> groups(
      								 @Parameter(description = "页码") @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                     @Parameter(description = "页面大小") @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                     @Parameter(description = "搜索关键词") @RequestParam(name = "key", required = false, defaultValue = "") String key) {
        return groupService.searchByKeyAndPage(key,
                                                page,
                                                size,
                                                Group.Fields.name); 
    }

    /**
     * 码本分组详情
     * 
     * @param id
     *            码本分组id
     * @return 码本分组
     */
    @GetMapping("group/{id}")
    	@Operation(summary ="码本分组详情")
		public Group groupDetail(@Parameter(description = "码本分组id", required = true) @PathVariable("id") long id) {
        return groupService.fetch(id);
    }

    /**
     * 添加或者更新码本分组
     * 
     * @param group
     *            码本分组数据
     * @return 码本分组
     */
    @PutMapping("group")
    @Operation(summary = "增加/编辑码本分组")
      public Group saveOrUpdateGroup(@Validated @Parameter(description ="码本分组")@RequestBody Group group) {
        if (group.getId() != null && group.getId() > 0) {
            if (groupService.update(group) == 1) {
                return group;
            } else {
                throw Lang.makeThrow("更新码本分组失败!");
            }
        }
        return groupService.insert(group);
    }

    /**
     * 删除码本分组
     * 
     * @param id
     *            码本分组id
     * @return 是否删除成功
     */
    @DeleteMapping("group/{id}")
    @Operation(summary = "删除码本分组")
      public void deleteGroup(@Parameter(description = "码本分组id", required = true)@PathVariable("id") long id) {
         if(groupService.delete(id) != 1){
         		throw Lang.makeThrow("删除码本分组失败!");
         }
    }

}
