package tech.riemann.nutz.demo.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tech.riemann.nutz.demo.entity.acl.Button;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
@Getter
@AllArgsConstructor
public enum InstalledButton {
    /**
     * 
     */
    ORGANIZATION_ADD("add_organization", "添加机构", "添加机构", InstalledMenu.ORGANIZATION),
    /**
     * 
     */
    ORGANIZATION_DETAIL("organization_detail", "机构详情", "机构详情", InstalledMenu.ORGANIZATION),
    /**
     * 
     */
    APPLICATION_ADD("add_application", "添加应用", "添加应用", InstalledMenu.APPLICATION),
    /**
     * 
     */
    APPLICATION_DETAIL("application_detail", "应用详情", "应用详情", InstalledMenu.APPLICATION),
    /**
     * 
     */
    APPLICATION_DETAIL_USER("application_detail_user", "应用用户", "应用用户", InstalledMenu.APPLICATION_DETAIL),
    /**
     * 
     */
    APPLICATION_DETAIL_ROLE("application_detail_role", "应用详情", "应用角色", InstalledMenu.APPLICATION_DETAIL)
    // TODO 类型列举
    ;

    String key;

    String name;

    String description;

    InstalledMenu menu;

    /**
     * 
     */
    public Button toButton() {
        return Button.builder()
                     .key(key)
                     .name(name)
                     .description(description)
                     .installed(true)
                     .menuKey(menu.key)
                     .build();
    }

    public static List<Button> buttons() {
        return Arrays.stream(values()).map(InstalledButton::toButton).collect(Collectors.toList());
    }

}
