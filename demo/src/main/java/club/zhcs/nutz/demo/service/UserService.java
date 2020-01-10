package club.zhcs.nutz.demo.service;

import java.util.List;

import org.nutz.spring.boot.service.BaseService;
import org.springframework.stereotype.Service;

import club.zhcs.nutz.demo.entity.acl.User;

/**
 * @author mdp 代码生成器
 */
@Service
public class UserService extends BaseService<User> {

    /**
     * @param key
     * @return
     */
    public List<String> permissions(String key) {
        return null;
    }

    /**
     * @param key
     * @return
     */
    public List<String> roles(String key) {
        return null;
    }

}
