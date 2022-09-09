package tech.riemann.nutz.demo.service.dictionary;

import org.nutz.dao.Dao;
import org.nutz.spring.boot.service.interfaces.IdEntityService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.entity.dictionary.Dictionary;
/**
 * 码本数据 服务类
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-09 23:34:36
 */
@Service
@RequiredArgsConstructor
public class DictionaryService implements IdEntityService<Dictionary> {

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
