package club.zhcs.nutz.demo.controller.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import club.zhcs.Result;
import club.zhcs.nutz.demo.entity.acl.Action;
import club.zhcs.nutz.demo.service.acl.ActionService;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
@RestController
public class ActionController {

    @Autowired
    ActionService actionService;

    @GetMapping("action/{id}")
    public Result<Action> get(@PathVariable("id") long id) {
        return Result.success(actionService.fetch(id));
    }

    @PostMapping("action")
    public Result<Action> add(@RequestBody Action action) {
        return Result.success(actionService.save(action));
    }

    @PutMapping("action")
    public Result<Void> edit(@RequestBody Action action) {
        return actionService.update(action, "name", "key") ? Result.success() : Result.fail("更新操作失败");
    }

    @DeleteMapping("action/{id}")
    public Result<Void> delete(@PathVariable("id") long id) {
        return actionService.delete(id) == 1 ? Result.success() : Result.fail("删除操作失败");
    }
}
