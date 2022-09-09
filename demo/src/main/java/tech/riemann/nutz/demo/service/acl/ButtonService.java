package tech.riemann.nutz.demo.service.acl;

import org.nutz.dao.Dao;
import org.nutz.spring.boot.service.interfaces.IdNameEntityService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.entity.acl.Button;
/**
 * 操作按钮 服务类
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-09
 */
@Service
@RequiredArgsConstructor
public class ButtonService implements IdNameEntityService<Button> {
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
