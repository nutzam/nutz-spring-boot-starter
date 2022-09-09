package tech.riemann.nutz.demo.service.acl;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.spring.boot.service.interfaces.IdNameEntityService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.entity.acl.User;

/**
 * 用户 服务类
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-09
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
}
