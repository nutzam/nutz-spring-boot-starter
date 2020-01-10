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
import club.zhcs.nutz.demo.entity.acl.Module;
import club.zhcs.nutz.demo.service.ModuleService;

/**
 * @author mdp 代码生成器
 */
@RestController
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @GetMapping("/modules")
    public Result<Pager<Module>> search(
                                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                        @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                        @RequestParam(value = "key", required = false) String key) {
        return Result.success(moduleService
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

    @GetMapping("/module/{id}")
    public Result<Module> get(@PathVariable("id") long id) {
        return Result.success(moduleService.fetch(id));
    }

    @PatchMapping("/module")
    public Result<Module> addOrEdit(@Validated @RequestBody Module module) {
        if (module.getId() > 0) {
            // TODO 确定更新字段
            return moduleService.update(module, "name", "email", "mobile") ? Result.success(module) : Result.fail("更新功能模块失败");
        }
        return Result.success(moduleService.save(module));
    }

    @DeleteMapping("/module/{id}")
    public Result delete(@PathVariable("id") long id) {
        return moduleService.delete(id) == 1 ? Result.success() : Result.fail("删除功能模块失败");
    }

}
