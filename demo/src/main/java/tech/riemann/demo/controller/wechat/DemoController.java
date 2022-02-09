package tech.riemann.demo.controller.wechat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import club.zhcs.Result;
import tech.riemann.demo.dto.A;
import tech.riemann.demo.dto.B;

/**
 * @author wkipy
 *
 */
@RestController
public class DemoController {

    @GetMapping("demo1")
    public Result<A<String, Long[]>> demo1() {
        return Result.success();
    }

    @GetMapping("demo2")
    public Result<A<String, B>> demo2() {
        return Result.success();
    }

}
