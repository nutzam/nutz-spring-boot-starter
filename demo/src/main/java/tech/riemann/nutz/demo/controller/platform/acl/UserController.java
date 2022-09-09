package tech.riemann.nutz.demo.controller.platform.acl;

import org.nutz.lang.Lang;
import org.nutz.spring.boot.service.entity.Pagination;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.entity.acl.User;
import tech.riemann.nutz.demo.service.acl.UserService;
/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-09 23:34:35
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "用户")
public class UserController {
	
    private final UserService userService;

    /**
     * 分页查询用户
     * 
     * @param page
     *            页码
     * @param size
     *            分页大小
     * @param key
     *            关键词
     * @return 用户分页数据
     */
    @GetMapping("users")
    @Operation(summary = "分页查询用户")
      public Pagination<User> users(
      								 @Parameter(description = "页码") @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                     @Parameter(description = "页面大小") @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                     @Parameter(description = "搜索关键词") @RequestParam(name = "key", required = false, defaultValue = "") String key) {
        return userService.searchByKeyAndPage(key,
                                                page,
                                                size,
                                                User.Fields.name); 
    }

    /**
     * 用户详情
     * 
     * @param id
     *            用户id
     * @return 用户
     */
    @GetMapping("user/{id}")
    	@Operation(summary ="用户详情")
		public User userDetail(@Parameter(description = "用户id", required = true) @PathVariable("id") long id) {
        return userService.fetch(id);
    }

    /**
     * 添加或者更新用户
     * 
     * @param user
     *            用户数据
     * @return 用户
     */
    @PutMapping("user")
    @Operation(summary = "增加/编辑用户")
    public User saveOrUpdateUser(@Validated @Parameter(description ="用户")@RequestBody User user) {
        if (user.getId() != null && user.getId() > 0) {
            if (userService.update(user) == 1) {
                return user;
            } else {
                throw Lang.makeThrow("更新用户失败!");
            }
        }
        return userService.insert(user);
    }

    /**
     * 删除用户
     * 
     * @param id
     *            用户id
     * @return 是否删除成功
     */
    @DeleteMapping("user/{id}")
    @Operation(summary = "删除用户")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@Parameter(description = "用户id", required = true)@PathVariable("id") long id) {
         if(userService.delete(id) != 1){
         		throw Lang.makeThrow("删除用户失败!");
         }
    }

}
