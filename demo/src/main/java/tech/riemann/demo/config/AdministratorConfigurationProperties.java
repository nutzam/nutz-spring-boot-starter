package tech.riemann.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kerbores(kerbores@gmail.com)
 */
@Data
@ConfigurationProperties("nutz.demo.acl")
public class AdministratorConfigurationProperties {

    String userName = "kerbores";

    String cypher = "G00dl^ck";

    String mobile = "18996359755";

    Role admin = Role.builder().build();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Role {
        @Default
        String key = "admin";
        @Default
        String name = "超级管理员";
        @Default
        String description = "超级管理员,创世角色,一切均在掌控之中";
    }
}
