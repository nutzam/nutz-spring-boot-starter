package org.nutz.demo.controller;

import org.nutz.dao.Dao;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.demo.bean.Student;
import org.nutz.lang.Lang;
import org.nutz.lang.random.R;
import org.nutz.spring.boot.service.entity.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import club.zhcs.Result;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@RestController
public class TestController {

    @Autowired
    Dao dao;

    @GetMapping("sqls")
    public Result<SqlManager> sql() {
        Result.<Student> success(new Student());
        return Result.success(dao.sqls());
    }

    public Result<Pager<Student>> name() {
        return Result.success(Pager.<Student> NEW()
                                   .page(1)
                                   .size(15)
                                   .recordCount(300)
                                   .dataList(Lang.list(new Student())));
    }

    @GetMapping("tpl")
    public Result<Integer> tpl() {
        Sql sql = dao.sqls().create("test.tpl");
        sql.vars().set("a", R.random(1, 10));
        sql.params().set("b", R.random(1, 10));
        sql.setCallback(Sqls.callback.integer());
        dao.execute(sql);
        return Result.success(sql.getInt());
    }

    @GetMapping("json")
    public Result<Student> json() {
        return Result.success(Student.builder().score(125644878.12155D).build());
    }

}
