package tech.riemann.nutz.demo.service.acl;

import java.util.List;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.lang.random.R;
import org.nutz.spring.boot.service.interfaces.EntityService;
import org.nutz.spring.boot.service.interfaces.IdNameEntityService;
import org.springframework.stereotype.Service;

import club.zhcs.auth.PasswordUtils;
import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.entity.acl.User;
import tech.riemann.nutz.demo.exception.BizException;

/**
 * 用户 服务类
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-09 23:38:21
 */
@Service
@RequiredArgsConstructor
public class UserService implements IdNameEntityService<User> {

    private final Dao dao;

    /**
     * @return
     * @see org.nutz.spring.boot.service.ExtService#dao()
     */
    @Override
    public Dao dao() {
        return dao;
    }

    /**
     * @param key
     * @return
     */
    public List<String> permissions(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param key
     * @return
     */
    public List<String> roles(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param name
     * @return
     */
    public String resetPassword(String name) {
        String newPassword = R.sg(10).next();
        if (update(Chain.make(User.Fields.password, PasswordUtils.randomSaltEncode(newPassword)), Cnd.where(User::getName, EntityService.EQ, name)) != 1) {
            throw BizException.create("重置密码失败!");
        }
        return newPassword;
    }
}
