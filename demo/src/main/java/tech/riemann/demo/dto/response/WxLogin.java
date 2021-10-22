package tech.riemann.demo.dto.response;

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
    boolean self_redirect = true;
    String id;

    String appid;

    @Default
    String scope = "snsapi_login";

    String redirect_uri;

    String state;
    @Default
    Style style = Style.black;
    String href;

    public enum Style {
        black, white
    }

}
