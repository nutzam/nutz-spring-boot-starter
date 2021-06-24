package club.zhcs.nutz.demo.dto.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PermissionInfo {

    @ApiModelProperty(value = "模块信息列表", required = true)
    List<ModuleInfo> modules;
}
