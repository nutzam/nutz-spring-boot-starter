package tech.riemann.demo.dto.request;

import java.util.List;

import org.nutz.lang.Lang;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VXETableSaveDTO<T> {
    @Default
    @Schema(description = "新增记录数据", required = true)
    List<T> insertRecords = Lang.list();

    @Default
    @Schema(description = "删除记录数据", required = true)
    List<T> removeRecords = Lang.list();

    @Default
    @Schema(description = "更新记录数据", required = true)
    List<T> updateRecords = Lang.list();

}
