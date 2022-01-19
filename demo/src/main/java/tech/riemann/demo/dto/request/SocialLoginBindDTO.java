package tech.riemann.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import tech.riemann.demo.entity.acl.LoginChannel.Channel;

/**
 * @author wkipy
 *
 */
@Data
@Schema(description = "社会化登录用户绑定数据对象")
public class SocialLoginBindDTO {

    @Schema(description = "手机号", required = true)
    String mobile;
    @Schema(description = "验证码", required = true)
    String code;
    @Schema(description = "openid", required = true)
    String openid;
    @Schema(description = "渠道", required = true)
    Channel channel;
}
