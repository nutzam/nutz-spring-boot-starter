package club.zhcs.nutz.demo.controller;

import java.util.List;
import java.util.Optional;

import org.nutz.spring.boot.service.entity.Pager;
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
import club.zhcs.nutz.demo.entity.acl.Action;
import club.zhcs.nutz.demo.entity.acl.Module;
import club.zhcs.nutz.demo.service.acl.ModuleService;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@RestController
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @GetMapping("modules")
    public Result<Pager<Module>> search(
                                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                        @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                        @RequestParam(value = "key", required = false) String key) {
        return Result.success(moduleService.searchByKeyAndPage(Optional.ofNullable(key).orElse(""),
                                                               page,
                                                               pageSize,
                                                               "key",
                                                               "name",
                                                               "descr")
                                           .addParam("key", key));
    }

    @GetMapping("module/{id}")
    public Result<Module> get(@PathVariable("id") long id) {
        return Result.success(moduleService.fetch(id));
    }

    @GetMapping("module/{id}/actions")
    public Result<List<Action>> actions(@PathVariable("id") long id) {
        return Result.success(moduleService.actions(id));
    }

    @PostMapping("module")
    public Result<Module> add(@RequestBody Module module) {
        return Result.success(moduleService.save(module));
    }

    @PutMapping("module")
    public Result edit(@RequestBody Module module) {
        return moduleService.update(module, "name", "descr") ? Result.success() : Result.fail("更新模块失败");
    }

    @DeleteMapping("module/{id}")
    public Result delete(@PathVariable("id") long id) {
        return moduleService.delete(id) == 1 ? Result.success() : Result.fail("删除模块失败");
    }
}
