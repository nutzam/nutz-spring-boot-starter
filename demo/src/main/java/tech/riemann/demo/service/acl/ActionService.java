package tech.riemann.demo.service.acl;

import javax.annotation.Resource;

import org.nutz.dao.Dao;
import org.nutz.spring.boot.service.IdNameBaseService;
import org.springframework.stereotype.Service;

import tech.riemann.demo.entity.acl.Action;

/**
 * <p>
 * 功能动作 服务实现类
 * </p>
 *
 * @author Kerbores
 * @since 2021-08-12
 */
@Service
public class ActionService extends IdNameBaseService<Action> {
    /* (non-Javadoc)
     * @see org.nutz.spring.boot.service.IdNameBaseService#init(org.nutz.dao.Dao)
     */
    @Override
    @Resource(type = Dao.class, name = "2ndDao")
    public void init(Dao dao) {
        super.init(dao);
    }
}
