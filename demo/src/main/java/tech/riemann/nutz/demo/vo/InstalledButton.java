package tech.riemann.nutz.demo.vo;

import tech.riemann.nutz.demo.entity.acl.Button;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
public enum InstalledButton {
    /**
     * 
     */
    ADD("add", "添加"),
    /**
     * 
     */
    DELETE("delete", "删除"),
    /**
     * 
     */
    EDIT("edit", "编辑"),
    /**
     * 
     */
    EXPORT("export", "导出"),
    /**
     * 
     */
    IMPORT("import", "导入"),
    /**
     * 
     */
    LIST("list", "列表");

    String key;

    String name;

    /**
     * @param key
     * @param name
     */
    private InstalledButton(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    /**
     * 
     */
    public Button toButton() {
        return Button.builder()
                     .key(getKey())
                     .name(getName())
                     .installed(true)
                     .build();
    }

}
