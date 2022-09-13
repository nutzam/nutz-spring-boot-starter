package tech.riemann.nutz.demo.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tech.riemann.nutz.demo.entity.acl.Menu;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
@Getter
@AllArgsConstructor
public enum InstalledMenu {
    /**
     * 
     */
    AUTH("auth", "认证中心", "统一认证中心", null),
    /**
     * 
     */
    ORGANIZATION("organization", "机构管理", "机构管理", "auth"),
    /**
     * 
     */
    ORGANIZATION_DETAIL("organization-detail", "机构详情", "机构详情", "organization"),
    /**
     * 
     */
    USER("user", "用户管理", "用户管理", "auth"),
    /**
     * 
     */
    APPLICATION("application", "应用管理", "应用管理", "auth"),
    /**
     * 
     */
    APPLICATION_DETAIL("application-detail", "应用详情", "应用详情", "application");

    String key;

    String name;

    String description;

    String parent;

    /**
     * 
     */
    public Menu toMenu() {
        return Menu.builder()
                   .description(description)
                   .key(key)
                   .name(name)
                   .parentKey(parent)
                   .build();
    }

    public static List<Menu> menus() {
        return Arrays.stream(values()).map(InstalledMenu::toMenu).collect(Collectors.toList());
    }
}
