package club.zhcs.nutz.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import club.zhcs.Result;
import club.zhcs.apm.APM;
import club.zhcs.nutz.demo.dto.request.TestIdCard;
import club.zhcs.nutz.demo.service.acl.RoleService;

@RestController
@APM
public class TestController {

    @Autowired
    RoleService roleService;

    @PostMapping("jsr303")
    public Result jsr303(@RequestBody @Validated TestIdCard idCard) {
        return Result.success();
    }

    @GetMapping("role/{id}/actions/info")
    public Result actions(@PathVariable("id") long id,
                          @RequestParam(value = "mids", required = false, defaultValue = "-1") Long[] moduleIds) {
        return Result.success(roleService.actionInfo(id, moduleIds));
    }
}
