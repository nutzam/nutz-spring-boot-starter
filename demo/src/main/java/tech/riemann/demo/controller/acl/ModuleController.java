package tech.riemann.demo.controller.acl;

import java.util.List;
import java.util.Optional;

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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import tech.riemann.demo.entity.acl.Action;
import tech.riemann.demo.entity.acl.Module;
import tech.riemann.demo.service.acl.ModuleService;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
@RestController
@Api(value = "Module", tags = {"功能模块"})
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @GetMapping("modules")
    @ApiOperation("分页查询系统功能")
    public Result<Pagination<Module>> search(
                                             @ApiParam("页面") @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                             @ApiParam("分页大小") @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                             @ApiParam("搜索关键词") @RequestParam(value = "key", required = false) String key) {

        return Result.success(moduleService.searchByKeyAndPage(Optional.ofNullable(key).orElse(""),
                                                               page,
                                                               pageSize,
                                                               Module.Fields.key,
                                                               Module.Fields.name,
                                                               Module.Fields.description)
                                           .addParam("key", key));
    }

    @GetMapping("module/{id}")
    @ApiOperation("根据id查询系统功能详情")
    public Result<Module> get(@ApiParam("系统功能id") @PathVariable("id") long id) {
        return Result.success(moduleService.fetch(id));
    }

    @GetMapping("module/{id}/actions")
    @ApiOperation("查询指定系统功能下的所有功能动作")
    public Result<List<Action>> actions(@ApiParam("系统功能id") @PathVariable("id") long id) {
        return Result.success(moduleService.actions(id));
    }

    @PostMapping("module")
    @ApiOperation("创建系统功能")
    public Result<Module> add(@ApiParam("系统功能数据") @RequestBody Module module) {
        return Result.success(moduleService.save(module));
    }

    @PutMapping("module")
    @ApiOperation("根据id更新系统功能")
    public Result<Void> edit(@ApiParam("系统功能数据") @RequestBody Module module) {
        return moduleService.update(module, "name", "description") ? Result.success() : Result.fail("更新模块失败");
    }

    @DeleteMapping("module/{id}")
    @ApiOperation("根据id删除系统功能")
    public Result<Void> delete(@ApiParam("系统功能id") @PathVariable("id") long id) {
        return moduleService.delete(id) == 1 ? Result.success() : Result.fail("删除模块失败");
    }
}