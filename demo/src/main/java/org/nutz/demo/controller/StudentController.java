package org.nutz.demo.controller;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.nutz.dao.Cnd;
import org.nutz.demo.bean.Student;
import org.nutz.demo.service.StudentService;
import org.nutz.lang.random.R;
import org.nutz.spring.boot.service.entity.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import club.zhcs.Result;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("students")
    public Result<Pager<Student>> search(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                         @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                         @RequestParam(value = "key", required = false) String key) {
        return Result.success(studentService.searchByKeyAndPage(
                                                                Optional.ofNullable(key).orElse(""),
                                                                page,
                                                                pageSize,
                                                                Cnd.NEW(),
                                                                "no",
                                                                "name")
                                            .addParam("key", key));
    }

    @GetMapping("init")
    public Result init() {
        studentService.save(Arrays.stream("1234567890".split(""))
                                  .map(item -> Student.builder()
                                                      .no(R.UU32())
                                                      .name(R.sg(10).next())
                                                      .build())
                                  .collect(Collectors.toList()));
        return Result.success();
    }

}
