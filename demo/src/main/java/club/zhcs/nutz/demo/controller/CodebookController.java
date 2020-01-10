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
import club.zhcs.nutz.demo.entity.code.Codebook;
import club.zhcs.nutz.demo.service.CodebookService;

/**
 * @author mdp 代码生成器
 */
@RestController
public class CodebookController {

    @Autowired
    CodebookService codebookService;

    @GetMapping("/codebooks")
    public Result<Pager<Codebook>> search(
                                          @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                          @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                          @RequestParam(value = "key", required = false) String key) {
        return Result.success(codebookService
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

    @GetMapping("/codebook/{id}")
    public Result<Codebook> get(@PathVariable("id") long id) {
        return Result.success(codebookService.fetch(id));
    }

    @PatchMapping("/codebook")
    public Result<Codebook> addOrEdit(@Validated @RequestBody Codebook codebook) {
        if (codebook.getId() > 0) {
            // TODO 确定更新字段
            return codebookService.update(codebook, "name", "email", "mobile") ? Result.success(codebook) : Result.fail("更新码本数据失败");
        }
        return Result.success(codebookService.save(codebook));
    }

    @DeleteMapping("/codebook/{id}")
    public Result delete(@PathVariable("id") long id) {
        return codebookService.delete(id) == 1 ? Result.success() : Result.fail("删除码本数据失败");
    }

}
