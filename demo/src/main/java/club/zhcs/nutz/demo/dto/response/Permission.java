package club.zhcs.nutz.demo.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"actionId", "moduleId"})
public class Permission {

    @ApiModelProperty(value = "操作key", required = true)
    String actionKey;

    @ApiModelProperty(value = "操作名称", required = true)
    String actionName;

    @ApiModelProperty(value = "操作Id", required = true)
    long actionId;

    @ApiModelProperty(value = "模块key", required = true)
    String moduleKey;

    @ApiModelProperty(value = "模块名称", required = true)
    String moduleName;

    @ApiModelProperty(value = "模块描述")
    String moduleDescription;

    @ApiModelProperty(value = "模块Id")
    long moduleId;

    @Default
    @ApiModelProperty(value = "操作选中标志")
    boolean actionSelected = true;

    @Default
    @ApiModelProperty(value = "模块选中标志")
    boolean moduleSelected = true;

    public ModuleInfo toModuleInfo() {
        return ModuleInfo.builder()
                         .descr(getModuleDescription())
                         .moduleName(getModuleName())
                         .moduleKey(getModuleKey())
                         .moduleId(getModuleId())
                         .selected(moduleSelected)
                         .build();
    }

    public ActionInfo toActionInfo() {
        return ActionInfo.builder()
                         .name(getActionName())
                         .key(getActionKey())
                         .id(getActionId())
                         .selected(actionSelected)
                         .build();
    }
}
