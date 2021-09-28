package tech.riemann.demo.service.acl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.spring.boot.service.IdNameBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.riemann.demo.entity.acl.Action;
import tech.riemann.demo.entity.acl.Module;

/**
 * <p>
 * 功能模块 服务实现类
 * </p>
 *
 * @author Kerbores
 * @since 2021-08-12
 */
@Service
public class ModuleService extends IdNameBaseService<Module> {

    @Autowired
    ActionService actionService;

    /**
     * @param id
     * @return
     */
    public List<Action> actions(long id) {
        return actionService.query(Cnd.where(Action.Fields.moduleId, "=", id).or(Action.Fields.moduleId, "=", 0));
    }
}
