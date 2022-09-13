package tech.riemann.nutz.demo.config;

import javax.annotation.PostConstruct;

import org.nutz.spring.boot.dao.NutzDatabaseInitializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import club.zhcs.auth.PasswordUtils;
import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.entity.acl.Role;
import tech.riemann.nutz.demo.entity.acl.RolePermission;
import tech.riemann.nutz.demo.entity.acl.User;
import tech.riemann.nutz.demo.entity.acl.UserRole;
import tech.riemann.nutz.demo.service.acl.ButtonService;
import tech.riemann.nutz.demo.service.acl.MenuService;
import tech.riemann.nutz.demo.service.acl.RolePermissionService;
import tech.riemann.nutz.demo.service.acl.RoleService;
import tech.riemann.nutz.demo.service.acl.UserRoleService;
import tech.riemann.nutz.demo.service.acl.UserService;

/**
 * 
 * @author kerbores(kerbores@gmail.com)
 *
 */
@Configuration
@ConditionalOnExpression("${nutz.admin.acl:true}")
@EnableConfigurationProperties(AdministratorConfigurationProperties.class)
@RequiredArgsConstructor
public class AdministratorInitializationAutoConfiguration {

    private final AdministratorConfigurationProperties configurationProperties;

    private final UserService userService;

    private final RoleService roleService;

    private final MenuService menuService;

    private final ButtonService buttonService;

    private final UserRoleService userRoleService;

    private final RolePermissionService rolePermissionService;

    private final NutzDatabaseInitializer nutzDatabaseInitializer;

    @PostConstruct
    public void init() {
        nutzDatabaseInitializer.hashCode();
        final String su = configurationProperties.getUserName();
        final String admin = configurationProperties.getAdmin().getKey();

        /**
         * 初始化用户
         */
        if (userService.countByNutz() == 0) {
            userService.insert(User.builder()
                                   .name(su)
                                   .mobile(configurationProperties.getMobile())
                                   .fullName(configurationProperties.getFullName())
                                   .email(configurationProperties.getEmail())
                                   .password(PasswordUtils.randomSaltEncode(configurationProperties.getCypher()))
                                   .build());
        }

        /**
         * 初始化角色
         */
        if (roleService.countByNutz() == 0) {
            roleService.insert(Role.builder()
                                   .key(admin)
                                   .name(configurationProperties.getAdmin().getName())
                                   .description(configurationProperties.getAdmin().getDescription())
                                   .build());
        }

        /**
         * 初始化角色权限
         */
        if (rolePermissionService.countByNutz() == 0) {
            menuService.queryByNutz()
                       .stream()
                       .forEach(resource -> buttonService.queryByNutz()
                                                         .stream()
                                                         .forEach(operate -> rolePermissionService.insert(RolePermission.builder()
                                                                                                                        .buttonKey(operate.getKey())
                                                                                                                        .menuKey(resource.getKey())
                                                                                                                        .roleKey(admin)
                                                                                                                        .build())));
        }

        /**
         * 给用户授予角色
         */
        if (userRoleService.countByNutz() == 0) {
            userRoleService.insert(UserRole.builder().userName(su).roleKey(admin).build());
        }
    }
}