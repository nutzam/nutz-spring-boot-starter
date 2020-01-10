package club.zhcs.nutz.demo.controller;

import java.util.Optional;

import org.nutz.spring.boot.service.entity.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import club.zhcs.Result;
import club.zhcs.nutz.demo.entity.code.Group;
import club.zhcs.nutz.demo.service.GroupService;

/**
 * @author mdp 代码生成器
 */
@RestController
public class GroupController {

    @Autowired
    GroupService groupService;

    @GetMapping("/groups")
    public Result<Pager<Group>> search(
                                       @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                       @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                       @RequestParam(value = "key", required = false) String key) {
        return Result.success(groupService
                                          .searchByKeyAndPage(Optional.ofNullable(key)
                                                                      .orElse(""),
                                                              page,
                                                              pageSize,
                                                              "name",
                                                              "email",
                                                              "mobile")
                                          .addParam("key", key));
        // TODO 确定查询字段
    }

    @GetMapping("/group/{id}")
    public Result<Group> get(@PathVariable("id") long id) {
        return Result.success(groupService.fetch(id));
    }

    @PatchMapping("/group")
    public Result<Group> addOrEdit(@Validated @RequestBody Group group) {
        if (group.getId() > 0) {
            // TODO 确定更新字段
            return groupService.update(group, "name", "email", "mobile") ? Result.success(group) : Result.fail("更新码本分组失败");
        }
        return Result.success(groupService.save(group));
    }

    @DeleteMapping("/group/{id}")
    public Result delete(@PathVariable("id") long id) {
        return groupService.delete(id) == 1 ? Result.success() : Result.fail("删除码本分组失败");
    }

}
