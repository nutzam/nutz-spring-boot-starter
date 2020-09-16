package org.nutz.spring.boot.dao;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
@Data
@ConfigurationProperties(prefix = "nutz.dao")
public class NutzDaoAutoConfigurationProperties {

    boolean enabled = true;
    /**
     * 运行期配置
     */
    Runtime runtime = new Runtime();

    /**
     * sql管理器配置
     */
    SqlManager sqlManager = new SqlManager();

    /**
     * sql 模板处理插件配置
     */
    SqlTemplate sqlTemplate = new SqlTemplate();

    /**
     * dao的全局设置
     */
    Global global = new Global();

    @Data
    public static class Global {
        /** 是否检查字段为数据库的关键字 */
        public boolean checkColumnNameKeyword = false;

        /** 是否把字段名用字符包裹来进行关键字逃逸 */
        public boolean forceWrapColumnName = false;

        /** 是否把字段名给变成大写 */
        public boolean forceUpperColumnName = false;

        public boolean forceHumpColumnName = false;

        /** varchar 字段的默认字段长度 */
        public int defaultVarcharWidth = 128;
    }

    @Data
    public static class SqlTemplate {
        public enum Type {
            BEETL, FREEMARKER, JETBRICK, VELOCITY
        }

        /**
         * 模版引擎类型
         */
        Type type = Type.BEETL;

        /**
         * 是否启用标识
         */
        boolean enable = false;

    }

    @Data
    public static class SqlManager {
        /**
         * 模式
         */
        private Mode mode;

        /**
         * 路径列表
         */
        private String[] paths;

        public enum Mode {
            FILE, XML
        }
    }

    @Data
    public class Runtime {
        /**
         * 自动建表
         */
        private boolean create = true;

        /**
         * 自动变更
         */
        private boolean migration = true;

        /**
         * 实体包名
         */
        private String[] basepackage;

        /**
         * 强制创建<删表重建>
         */
        private boolean foceCreate = false;

        /**
         * 是否增加列
         */
        private boolean addColumn = true;

        /**
         * 是否删除列
         */
        private boolean deleteColumn = true;

        /**
         * 检查索引
         */
        private boolean checkIndex = true;
    }

}
