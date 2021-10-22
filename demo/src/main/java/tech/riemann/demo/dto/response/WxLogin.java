package tech.riemann.demo.dto.response;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(required = true)
    boolean self_redirect = true;

    @ApiModelProperty(required = true)
    String id;

    @ApiModelProperty(required = true)
    String appid;

    @Default
    @ApiModelProperty(required = true)
    String scope = "snsapi_login";

    @ApiModelProperty(required = true)
    String redirect_uri;

    @ApiModelProperty(required = true)
    String state;

    @Default
    Style style = Style.black;

    String href;

    public enum Style {
        black, white
    }

}
