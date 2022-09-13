package tech.riemann.nutz.demo.dto.response;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import tech.riemann.nutz.demo.entity.acl.Button;
import tech.riemann.nutz.demo.entity.acl.Menu;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Accessors(chain = true)
@Schema(name = "MenuInfo", description = "菜单信息")
public class MenuInfo extends Menu {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Button> buttons;

    public static List<MenuInfo> from(List<PermissionInfo> permissions) {
        Map<MenuInfo, List<PermissionInfo>> groupedMap = permissions.stream()
                                                                    .filter(PermissionInfo::isSelected)
                                                                    .collect(Collectors.groupingBy(PermissionInfo::toMenuInfo));

        return groupedMap.keySet().stream().map(item -> {
            item.setButtons(groupedMap.get(item)
                                      .stream()
                                      .map(PermissionInfo::toButton)
                                      .collect(Collectors.toList()));
            return item;
        }).collect(Collectors.toList());
    }
}
