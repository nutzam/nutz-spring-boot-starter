package club.zhcs.nutz.demo.controller;

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
import club.zhcs.nutz.demo.dto.request.VXETableSaveDTO;
import club.zhcs.nutz.demo.entity.code.CodeBook;
import club.zhcs.nutz.demo.service.codebook.CodeBookService;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@RestController
public class CodeBookController {

    @Autowired
    CodeBookService codeBookService;

    @GetMapping("group/{id}/codes")
    public Result<List<CodeBook>> byGroup(@PathVariable("id") Long groupId) {
        return Result.success(codeBookService.query(Cnd.where("groupId", "=", groupId).asc("index").asc("id")));
    }

    @GetMapping("code/{id}")
    public Result<CodeBook> get(@PathVariable("id") long id) {
        return Result.success(codeBookService.fetch(id));
    }

    @PostMapping("code")
    public Result<CodeBook> add(@RequestBody CodeBook codeBook) {
        return Result.success(codeBookService.save(codeBook));
    }

    @PutMapping("code")
    public Result edit(@RequestBody CodeBook codeBook) {
        return codeBookService.update(codeBook, "index", "name", "description") ? Result.success() : Result.fail("更新码本失败");
    }

    @DeleteMapping("code/{id}")
    public Result delete(@PathVariable("id") long id) {
        return codeBookService.update(Chain.make("disabled", true), Cnd.where("id", "=", id)) == 1 ? Result.success()
                                                                                                   : Result.fail("删除码本失败");
    }

    @PostMapping("code/vxe/save")
    public Result vxeSave(@RequestBody VXETableSaveDTO<CodeBook> vxeData) {
        return codeBookService.vxeSave(vxeData) ? Result.success() : Result.fail("保存失败!");
    }
}
