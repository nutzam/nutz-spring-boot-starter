package club.zhcs.nutz.demo.service.acl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.spring.boot.service.IdNameBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import club.zhcs.nutz.demo.entity.acl.Action;
import club.zhcs.nutz.demo.entity.acl.Module;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
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
        return actionService.query(Cnd.where("moduleId", "=", id).or("moduleId", "=", 0));
    }

}
