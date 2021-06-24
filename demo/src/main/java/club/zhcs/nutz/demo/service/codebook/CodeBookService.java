package club.zhcs.nutz.demo.service.codebook;

import java.util.stream.Collectors;

import org.nutz.spring.boot.service.IdNameBaseService;
import org.springframework.stereotype.Service;

import club.zhcs.nutz.demo.dto.request.VXETableSaveDTO;
import club.zhcs.nutz.demo.entity.code.CodeBook;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
@Service
public class CodeBookService extends IdNameBaseService<CodeBook> {

    /**
     * @param data
     * @return
     */
    public boolean vxeSave(VXETableSaveDTO<CodeBook> data) {
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
