package club.zhcs.nutz.demo.vo;

import club.zhcs.nutz.demo.entity.acl.Action;

/**
 * @author Kerbores(kerbores@gmail.com)
 */
public enum InstalledAction {
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
    private InstalledAction(String key, String name) {
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
    public Action toAction() {
        return Action.builder()
                     .key(getKey())
                     .name(getName())
                     .installed(true)
                     .moduleId(0L)
                     .build();
    }

}
