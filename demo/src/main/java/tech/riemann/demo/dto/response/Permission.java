package tech.riemann.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
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
@EqualsAndHashCode(of = {"actionId", "moduleId"})
public class Permission {

    @Schema(description = "操作key", required = true)
    String actionKey;

    @Schema(description = "操作名称", required = true)
    String actionName;

    @Schema(description = "操作Id", required = true)
    long actionId;

    @Schema(description = "模块key", required = true)
    String moduleKey;

    @Schema(description = "模块名称", required = true)
    String moduleName;

    @Schema(description = "模块描述")
    String moduleDescription;

    @Schema(description = "模块Id")
    long moduleId;

    @Default
    @Schema(description = "操作选中标志")
    boolean actionSelected = true;

    @Default
    @Schema(description = "模块选中标志")
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
