package tech.riemann.nutz.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Kerbores(kerbores@riemann.tech)
 *
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"code", "message"})
@Schema(description = "全局错误")
public class GlobalError {

    @Schema(description = "错误码")
    int code;

    @Schema(description = "错误信息")
    String message;
}
