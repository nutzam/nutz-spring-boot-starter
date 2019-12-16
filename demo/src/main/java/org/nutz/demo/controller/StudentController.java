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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("student/{id}")
    public Result<Student> get(@PathVariable("id") long id) {
        return Result.success(studentService.fetch(id));
    }

    @PostMapping("student")
    public Result<Student> add(@RequestBody Student student) {
        return Result.success(studentService.save(student));
    }

    @PutMapping("student")
    public Result edit(@RequestBody Student student) {
        return studentService.update(student, "name", "no") ? Result.success() : Result.fail("更新学生信息失败");
    }

    @PatchMapping("student")
    public Result<Student> addOrEdit(@RequestBody Student student) {
        if (student.getId() > 0) {
            return studentService.update(student, "name", "no") ? Result.success(student) : Result.fail("更新学生信息失败");
        } else {
            return Result.success(studentService.save(student));
        }
    }

    @DeleteMapping("student/{id}")
    public Result delete(@PathVariable("id") long id) {
        return studentService.delete(id) == 1 ? Result.success() : Result.fail("删除学生信息失败");
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
