package tech.riemann.demo.controller.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import club.zhcs.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import tech.riemann.demo.entity.acl.Action;
import tech.riemann.demo.service.acl.ActionService;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
@RestController
@Tag(name = "Action", description = "功能动作模块")
public class ActionController {

    @Autowired
    ActionService actionService;

    @GetMapping("action/{id}")
    @Operation(summary = "根据id获取功能动作详情")
    public Result<Action> get(@Parameter(description = "功能动作id") @PathVariable("id") long id) {
        return Result.success(actionService.fetch(id));
    }

    @PostMapping("action")
    @Operation(summary = "新增功能动作")
    public Result<Action> add(@Parameter(description = "功能动作数据") @RequestBody Action action) {
        return Result.success(actionService.save(action));
    }

    @PutMapping("action")
    @Operation(summary = "根据id更新功能动作")
    public Result<Void> edit(@Parameter(description = "功能动作数据") @RequestBody Action action) {
        return actionService.update(action, "name", "key") ? Result.success() : Result.fail("更新操作失败");
    }

    @DeleteMapping("action/{id}")
    @Operation(summary = "根据id获取功能动作详情")
    public Result<Void> delete(@Parameter(description = "功能动作id") @PathVariable("id") long id) {
        return actionService.delete(id) == 1 ? Result.success() : Result.fail("删除操作失败");
    }
}
