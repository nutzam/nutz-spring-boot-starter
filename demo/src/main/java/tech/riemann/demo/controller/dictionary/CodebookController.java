package tech.riemann.demo.controller.dictionary;

import java.util.List;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
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
import tech.riemann.demo.dto.request.VXETableSaveDTO;
import tech.riemann.demo.entity.dictionary.Codebook;
import tech.riemann.demo.service.dictionary.CodebookService;

/**
 * <p>
 * 码本数据 前端控制器
 * </p>
 *
 * @author Kerbores
 * @since 2021-08-12
 */
@RestController
@Tag(name = "Codebook", description = "码本数据模块")
public class CodebookController {

    @Autowired
    CodebookService codebookService;

    @GetMapping("group/{id}/codes")
    @Operation(summary = "获取指定分组下的全部字典")
    public Result<List<Codebook>> byGroup(
                                          @Parameter(description = "分组id") @PathVariable("id") Long groupId) {
        return Result.success(codebookService.query(Cnd.where("groupId", "=", groupId).asc("index").asc("id")));
    }

    @GetMapping("code/{id}")
    @Operation(summary = "获取指定字典项")
    public Result<Codebook> get(
                                @Parameter(description = "字典项id") @PathVariable("id") long id) {

        return Result.success(codebookService.fetch(id));
    }

    @PostMapping("code")
    @Operation(summary = "新增字典项")
    public Result<Codebook> add(
                                @Parameter(description = "字典项数据") @RequestBody Codebook codebook) {

        return Result.success(codebookService.save(codebook));
    }

    @PutMapping("code")
    @Operation(summary = "根据id更新字典项")
    public Result<Void> edit(
                             @Parameter(description = "字典项数据") @RequestBody Codebook codebook) {

        return codebookService.update(codebook, "index", "name", "description") ? Result.success() : Result.fail("更新码本失败");
    }

    @DeleteMapping("code/{id}")
    @Operation(summary = "禁用指定字典项")
    public Result<Void> delete(
                               @Parameter(description = "字典项id") @PathVariable("id") long id) {

        return codebookService.update(Chain.make("disabled", true), Cnd.where("id", "=", id)) == 1
                                                                                                   ? Result.success()
                                                                                                   : Result.fail("删除码本失败");
    }

    @PostMapping("code/vxe/save")
    @Operation(summary = "保存指定分组下的字典项变更情况")
    public Result<Void> vxeSave(
                                @Parameter(description = "VXETable字典项数据情况") @RequestBody VXETableSaveDTO<Codebook> vxeData) {
        return codebookService.vxeSave(vxeData)
                                                ? Result.success()
                                                : Result.fail("保存失败!");
    }

}
