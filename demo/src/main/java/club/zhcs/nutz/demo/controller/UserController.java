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
import club.zhcs.nutz.demo.entity.acl.User;
import club.zhcs.nutz.demo.service.UserService;

/**
 * @author mdp 代码生成器
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public Result<Pager<User>> search(
                                      @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                      @RequestParam(value = "size", required = false, defaultValue = "15") int pageSize,
                                      @RequestParam(value = "key", required = false) String key) {
        return Result.success(userService
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

    @GetMapping("/user/{id}")
    public Result<User> get(@PathVariable("id") long id) {
        return Result.success(userService.fetch(id));
    }

    @PatchMapping("/user")
    public Result<User> addOrEdit(@Validated @RequestBody User user) {
        if (user.getId() > 0) {
            // TODO 确定更新字段
            return userService.update(user, "name", "email", "mobile") ? Result.success(user) : Result.fail("更新用户失败");
        }
        return Result.success(userService.save(user));
    }

    @DeleteMapping("/user/{id}")
    public Result delete(@PathVariable("id") long id) {
        return userService.delete(id) == 1 ? Result.success() : Result.fail("删除用户失败");
    }

}
