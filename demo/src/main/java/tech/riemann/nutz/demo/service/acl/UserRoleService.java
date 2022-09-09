package tech.riemann.nutz.demo.service.acl;

import org.nutz.dao.Dao;
import org.nutz.spring.boot.service.interfaces.IdEntityService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.entity.acl.UserRole;
/**
 * 用户角色 服务类
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-09 23:38:21
 */
@Service
@RequiredArgsConstructor
public class UserRoleService implements IdEntityService<UserRole> {

    private final Dao dao;

    /**
     * @return
     * @see org.nutz.spring.boot.service.ExtService#dao()
     */
    @Override
    public Dao dao() {
        return dao;
    }
}
