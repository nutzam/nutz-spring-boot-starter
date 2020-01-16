package club.zhcs.nutz.demo.dto.request;

import club.zhcs.jsr303.IDCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestIdCard {

    @IDCard
    String idCard;

}
