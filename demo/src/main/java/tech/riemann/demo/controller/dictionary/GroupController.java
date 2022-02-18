package tech.riemann.demo.controller.dictionary;

import java.util.Optional;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import tech.riemann.demo.entity.dictionary.Group;
import tech.riemann.demo.service.dictionary.GroupService;

/**
 * <p>
 * 码本分组 前端控制器
 * </p>
 *
 * @author Kerbores
 * @since 2021-08-12
 */
@RestController
@Tag(name = "Group", description = "码本分组模块")
public class GroupController {

    @Autowired
    GroupService groupService;

    @GetMapping("groups")
    @Operation(summary = "分页加载字典分组")
    public Result<Pagination<Group>> search(
                                            @Parameter(description = "页面") @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                            @Parameter(description = "分页大小") @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                            @Parameter(description = "状态筛选 true(启用)/false(禁用)/null(不过滤)") @RequestParam(value = "state", required = false) Boolean state,
                                            @Parameter(description = "搜索关键词") @RequestParam(value = "key", required = false) String key) {

        return Result.success(groupService.searchByKeyAndPage(Optional.ofNullable(key)
                                                                      .orElse(""),
                                                              page,
                                                              pageSize,
                                                              Cnd.NEW().andEX("disabled", "=", state),
                                                              Group.Fields.key,
                                                              Group.Fields.name,
                                                              Group.Fields.description)
                                          .addParam("key", key));
    }

    @GetMapping("group/{id}")
    @Operation(summary = "获取指定字典分组")
    public Result<Group> get(
                             @Parameter(description = "字典分组id") @PathVariable("id") long id) {

        return Result.success(groupService.fetch(id));
    }

    @PostMapping("group")
    @Operation(summary = "新增字典分组")
    public Result<Group> add(
                             @Parameter(description = "字典分组数据") @RequestBody Group group) {

        return Result.success(groupService.insert(group));
    }

    @PutMapping("group")
    @Operation(summary = "根据id更新字典分组")
    public Result<Void> edit(
                             @Parameter(description = "字典分组数据") @RequestBody Group group) {

        return groupService.update(group, "name", "description") ? Result.success() : Result.fail("更新分组失败");
    }

    @DeleteMapping("group/{id}")
    @Operation(summary = "删除字典分组")
    public Result<Void> delete(
                               @Parameter(description = "字典分组id") @PathVariable("id") long id) {

        return groupService.update(Chain.make(Group.Fields.disabled, true), Cnd.where("id", "=", id)) == 1 ? Result.success() : Result.fail("删除分组失败");
    }

}
