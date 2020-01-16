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
@EqualsAndHashCode
public class ActionInfo {

    @ApiModelProperty(value = "操作key", required = true)
    String key;

    @ApiModelProperty(value = "操作名称", required = true)
    String name;

    @ApiModelProperty(value = "操作id", required = true)
    long id;

    @Default
    @ApiModelProperty(value = "是否选中标识")
    boolean selected = true;
}
