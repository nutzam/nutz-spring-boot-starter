package club.zhcs.nutz.demo.dto.request;

import club.zhcs.auth.AuthService.LoginDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Login extends LoginDto {

    @ApiModelProperty(value = "验证码uuid", required = true)
    String uuid;

    @ApiModelProperty(value = "验证码", required = true)
    String captcha;
}
