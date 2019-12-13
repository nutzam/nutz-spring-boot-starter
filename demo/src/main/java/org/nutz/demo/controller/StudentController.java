package org.nutz.demo.controller;

import java.util.Optional;

import org.nutz.demo.bean.Student;
import org.nutz.demo.service.StudentService;
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
		return Result
				.success(studentService
						.searchByKeyAndPage(Optional.ofNullable(key).orElse(""), page, pageSize, "no", "name",
								"description")
						.addParam("key", key));
	}

}
