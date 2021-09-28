package tech.riemann.demo.dto.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.riemann.demo.entity.acl.Module;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "moduleId")
public class ModuleInfo {

    @ApiModelProperty(value = "模块名称", required = true)
    String moduleName;

    @ApiModelProperty(value = "模块key", required = true)
    String moduleKey;

    @ApiModelProperty(value = "模块描述")
    String descr;

    @ApiModelProperty(value = "模块Id")
    long moduleId;

    @ApiModelProperty(value = "模块操作列表", required = true)
    List<ActionInfo> actions;

    @Default
    @ApiModelProperty(value = "是否选中标识")
    boolean selected = true;

    public Module toModule() {
        return Module.builder()
                     .key(moduleKey)
                     .description(descr)
                     .name(moduleName)
                     .id(moduleId)
                     .build();
    }

}
