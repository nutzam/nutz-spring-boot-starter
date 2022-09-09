package tech.riemann.nutz.demo.vo;

import tech.riemann.nutz.demo.entity.acl.Menu;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
public enum InstalledMenu {
    /**
     * 
     */
    USER("user", "用户", "用户管理"),
    /**
     * 
     */
    ROLE("role", "角色", "角色管理"),
    /**
     * 
     */
    MODULE("module", "功能模块", "模块管理"),
    /**
     * 
     */
    ACTION("action", "操作按钮", "操作管理");

    String key;

    String name;

    String description;

    /**
     * @param key
     * @param name
     */
    private InstalledMenu(String key, String name, String description) {
        this.key = key;
        this.name = name;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 
     */
    public Menu toMenu() {
        return Menu.builder()
                   .description(getDescription())
                   .key(getKey())
                   .name(getName())
                   .build();
    }
}
