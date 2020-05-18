package club.zhcs.nutz.demo.controller;

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
import club.zhcs.nutz.demo.entity.code.Group;
import club.zhcs.nutz.demo.service.codebook.GroupService;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@RestController
public class GroupController {

    @Autowired
    GroupService groupService;

    @GetMapping("groups")
    public Result<Pagination<Group>> search(
                                       @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                       @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                       @RequestParam(value = "state", required = false) Boolean state,
                                       @RequestParam(value = "key", required = false) String key) {
        return Result.success(groupService.searchByKeyAndPage(Optional.ofNullable(key)
                                                                      .orElse(""),
                                                              page,
                                                              pageSize,
                                                              Cnd.NEW().andEX("disabled", "=", state),
                                                              "key",
                                                              "name",
                                                              "description")
                                          .addParam("key", key));
    }

    @GetMapping("group/{id}")
    public Result<Group> get(@PathVariable("id") long id) {
        return Result.success(groupService.fetch(id));
    }

    @PostMapping("group")
    public Result<Group> add(@RequestBody Group group) {
        return Result.success(groupService.save(group));
    }

    @PutMapping("group")
    public Result edit(@RequestBody Group group) {
        return groupService.update(group, "name", "description") ? Result.success() : Result.fail("更新分组失败");
    }

    @DeleteMapping("group/{id}")
    public Result delete(@PathVariable("id") long id) {
        return groupService.update(Chain.make("disabled", true), Cnd.where("id", "=", id)) == 1 ? Result.success() : Result.fail("删除分组失败");
    }
}
