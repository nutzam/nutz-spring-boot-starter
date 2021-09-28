package tech.riemann.demo.service.dictionary;

import java.util.stream.Collectors;

import org.nutz.spring.boot.service.IdBaseService;
import org.springframework.stereotype.Service;

import tech.riemann.demo.dto.request.VXETableSaveDTO;
import tech.riemann.demo.entity.dictionary.Codebook;

/**
 * <p>
 * 码本数据 服务实现类
 * </p>
 *
 * @author Kerbores
 * @since 2021-08-12
 */
@Service
public class CodebookService extends IdBaseService<Codebook> {

    /**
     * @param data
     * @return
     */
    public boolean vxeSave(VXETableSaveDTO<Codebook> data) {
        dao().insert(data.getInsertRecords());
        data.getRemoveRecords().stream().forEach(codebook -> delete(codebook.getId()));
        data.getUpdateRecords().stream().filter(codebook -> codebook.getId() > 0).forEach(this::update);
        dao().insert(data.getUpdateRecords()
                         .stream()
                         .filter(codebook -> codebook.getId() == 0)
                         .collect(Collectors.toList()));
        return true;
    }

}
