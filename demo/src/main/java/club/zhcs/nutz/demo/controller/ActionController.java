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
import club.zhcs.nutz.demo.entity.acl.Action;
import club.zhcs.nutz.demo.service.ActionService;

/**
 * @author mdp 代码生成器
 */
@RestController
public class ActionController {

    @Autowired
    ActionService actionService;

    @GetMapping("/actions")
    public Result<Pager<Action>> search(
                                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                        @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                        @RequestParam(value = "key", required = false) String key) {
        return Result.success(actionService
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

    @GetMapping("/action/{id}")
    public Result<Action> get(@PathVariable("id") long id) {
        return Result.success(actionService.fetch(id));
    }

    @PatchMapping("/action")
    public Result<Action> addOrEdit(@Validated @RequestBody Action action) {
        if (action.getId() > 0) {
            // TODO 确定更新字段
            return actionService.update(action, "name", "email", "mobile") ? Result.success(action) : Result.fail("更新功能动作失败");
        }
        return Result.success(actionService.save(action));
    }

    @DeleteMapping("/action/{id}")
    public Result delete(@PathVariable("id") long id) {
        return actionService.delete(id) == 1 ? Result.success() : Result.fail("删除功能动作失败");
    }

}
