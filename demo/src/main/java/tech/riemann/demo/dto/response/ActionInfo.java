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
@EqualsAndHashCode
public class ActionInfo {

    @Schema(description = "操作key", required = true)
    String key;

    @Schema(description = "操作名称", required = true)
    String name;

    @Schema(description = "操作id", required = true)
    long id;

    @Default
    @Schema(description = "是否选中标识")
    boolean selected = true;
}
