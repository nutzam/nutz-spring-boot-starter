package tech.riemann.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wkipy
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxLogin {
    @Default
    @Schema(required = true)
    boolean self_redirect = true;

    @Schema(required = true)
    String id;

    @Schema(required = true)
    String appid;

    @Default
    @Schema(required = true)
    String scope = "snsapi_login";

    @Schema(required = true)
    String redirect_uri;

    @Schema(required = true)
    String state;

    @Default
    Style style = Style.black;

    String href;

    public enum Style {
        black, white
    }

}
