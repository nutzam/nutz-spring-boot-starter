package org.nutz.demo.controller;

import org.nutz.dao.Dao;
import org.nutz.dao.SqlManager;
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
        return Result.success(dao.sqls());
    }
}
