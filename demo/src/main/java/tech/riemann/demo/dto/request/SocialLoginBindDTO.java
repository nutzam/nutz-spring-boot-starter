package tech.riemann.demo.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.riemann.demo.entity.acl.LoginChannel.Channel;

/**
 * @author wkipy
 *
 */
@Data
@ApiModel("社会化登录用户绑定数据对象")
public class SocialLoginBindDTO {

    @ApiModelProperty(value = "手机号", required = true)
    String mobile;
    @ApiModelProperty(value = "验证码", required = true)
    String code;
    @ApiModelProperty(value = "openid", required = true)
    String openid;
    @ApiModelProperty(value = "渠道", required = true)
    Channel channel;
}
