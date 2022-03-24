package tech.riemann.demo.service.acl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.nutz.dao.Cnd;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.Tasks;
import org.nutz.spring.boot.service.IdNameBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import club.zhcs.auth.PasswordUtils;
import club.zhcs.utils.DateUtils;
import tech.riemann.demo.config.AdministratorConfigurationProperties;
import tech.riemann.demo.dto.response.ModuleInfo;
import tech.riemann.demo.dto.response.Permission;
import tech.riemann.demo.dto.response.PermissionInfo;
import tech.riemann.demo.dto.response.RoleInfo;
import tech.riemann.demo.entity.acl.Module;
import tech.riemann.demo.entity.acl.Role;
import tech.riemann.demo.entity.acl.RolePermission;
import tech.riemann.demo.entity.acl.User;
import tech.riemann.demo.entity.acl.UserPermission;
import tech.riemann.demo.entity.acl.UserRole;
import tech.riemann.demo.vo.InstalledAction;
import tech.riemann.demo.vo.InstalledModule;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author Kerbores
 * @since 2021-08-12
 */
@Service
@EnableConfigurationProperties(AdministratorConfigurationProperties.class)
public class UserService extends IdNameBaseService<User> {

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RolePermissionService rolePermissionService;

    @Autowired
    UserPermissionService userPermissionService;

    @Autowired
    ModuleService moduleService;

    @Autowired
    ActionService actionService;

    @Autowired
    AdministratorConfigurationProperties configurationProperties;

    static final String USER_ID = "userId";

    /**
     * -全部角色
     * 
     * @param userId
     *            用户id
     * @return 角色列表
     */
    public List<String> roles(long userId) {
        return roleService.roleInfos(userId);
    }

    /**
     * -全部角色
     * 
     * @param userName
     *            用户名
     * @return 角色列表
     */
    public List<String> roles(String userName) {
        return roles(userId(userName));
    }

    /**
     * -全部权限
     * 
     * @param userId
     *            用户id
     * @return 权限列表
     */
    public List<String> permissions(long userId) {
        return Stream.of(direct(userId), indirect(userId))
                     .flatMap(Collection::stream)
                     .distinct()
                     .collect(Collectors.toList());
    }

    /**
     * -直接权限
     * 
     * @param userId
     *            用户id
     * @return 权限列表
     */
    public List<String> direct(long userId) {
        return directPermissions(userId).stream()
                                        .map(item -> String.format("%s.%s", item.getModuleKey(), item.getActionKey()))
                                        .distinct()
                                        .collect(Collectors.toList());
    }

    /**
     * -间接权限
     * 
     * @param userId
     *            用户id
     * @return 权限列表
     */
    public List<String> indirect(long userId) {
        return indirectPermissions(userId).stream()
                                          .map(item -> String.format("%s.%s", item.getModuleKey(), item.getActionKey()))
                                          .distinct()
                                          .collect(Collectors.toList());
    }

    /**
     * 查询权限
     * 
     * @param sqlKey
     *            sqlkey
     * @param userId
     *            用户id
     * @return 权限列表
     */
    public List<Permission> permissions(String sqlKey, long userId) {
        return list(createSql(sqlKey)
                                     .setParam(USER_ID, userId),
                    Permission.class);
    }

    public List<Permission> indirectPermissions(long userId) {
        return permissions("list.indirect.permissions.by.user.id", userId);
    }

    public List<Permission> directPermissions(long userId) {
        return permissions("list.direct.permissions.by.user.id", userId);
    }

    public List<Permission> allPermissions(long userId) {
        return Stream.of(directPermissions(userId), indirectPermissions(userId))
                     .flatMap(Collection::stream)
                     .distinct()
                     .collect(Collectors.toList());
    }

    private PermissionInfo toPermissionInfo(List<Permission> permissions) {
        Map<ModuleInfo, List<Permission>> groupedMap = permissions.stream().collect(Collectors.groupingBy(Permission::toModuleInfo));
        return PermissionInfo.builder()
                             .modules(groupedMap.keySet().stream().map(item -> {
                                 item.setActions(groupedMap.get(item)
                                                           .stream()
                                                           .map(Permission::toActionInfo)
                                                           .collect(Collectors.toList()));
                                 return item;
                             }).collect(Collectors.toList()))
                             .build();
    }

    public PermissionInfo indirectPermissionInfo(long userId) {
        return toPermissionInfo(indirectPermissions(userId));
    }

    public PermissionInfo directPermissionInfo(long userId) {
        return toPermissionInfo(directPermissions(userId));
    }

    public PermissionInfo permissionInfo(long userId) {
        return toPermissionInfo(allPermissions(userId));
    }

    /**
     * -全部权限
     * 
     * @param userName
     *            用户名
     * @return 权限列表
     */
    public List<String> permissions(String userName) {
        return permissions(userId(userName));
    }

    /**
     * 用户名转id
     * 
     * @param userName
     *            用户名
     * @return 用户id
     */
    public long userId(String userName) {
        return Optional.ofNullable(fetch(userName)).orElse(User.builder().build()).getId();
    }

    @PostConstruct
    public void postConstruct() {
        Tasks.scheduleAtFixedTime(this::init, DateUtils.addSeconds(15));
    }

    /**
     * 初始化数据
     */
    public void init() {
        final String su = configurationProperties.getUserName();
        final String admin = configurationProperties.getAdmin().getKey();
        User suUser = fetch(su);
        // 用户
        if (suUser == null) {
            suUser = save(User.builder()
                              .name(su)
                              .mobile(configurationProperties.getMobile())
                              .password(PasswordUtils.randomSaltEncode(configurationProperties.getCypher()))
                              .build());
        }
        // 角色
        Role adminRole = roleService.fetch(admin);
        if (adminRole == null) {
            adminRole = roleService.save(Role.builder()
                                             .key(admin)
                                             .name(configurationProperties.getAdmin().getName())
                                             .description(configurationProperties.getAdmin().getDescription())
                                             .build());
        }
        final long adminRoleId = adminRole.getId();
        Lang.list(InstalledModule.values()).stream().forEach(module -> {
            if (moduleService.fetch(module.getKey()) == null) {
                moduleService.save(module.toModule());
            }
        });
        Lang.list(InstalledAction.values()).stream().forEach(action -> {
            if (actionService.fetch(action.getKey()) == null) {
                actionService.save(action.toAction());
            }
        });

        // 给超级用户全部权限
        moduleService.query().stream().forEach(module -> actionService.query().stream().forEach(action -> {
            if (rolePermissionService.fetch(Cnd.where("actionKey", "=", action.getKey())
                                               .and("roleId", "=", adminRoleId)
                                               .and("moduleId", "=", module.getId())) == null) {
                rolePermissionService.save(RolePermission.builder()
                                                         .actionKey(action.getKey())
                                                         .moduleId(module.getId())
                                                         .moduleKey(module.getKey())
                                                         .roleId(adminRoleId)
                                                         .build());
            }
        }));
        // 角色用户关系
        if (userRoleService.fetch(Cnd.where("roleId", "=", adminRole.getId()).and(USER_ID, "=", suUser.getId())) == null) {
            userRoleService.save(UserRole.builder()
                                         .userId(suUser.getId())
                                         .roleId(adminRoleId)
                                         .build());
        }

    }

    /**
     * @param id
     * @param moduleIds
     * @return
     */
    public PermissionInfo actionInfo(long id, Long[] moduleIds) {
        return toPermissionInfo(actions(id, moduleIds));
    }

    public List<Permission> actions(Long id, Long... moduleIds) {
        List<Long> ids = Stream.of(modules(id).stream().map(Module::getId).collect(Collectors.toList()), Lang.list(moduleIds))
                               .flatMap(Collection::stream)
                               .distinct()
                               .collect(Collectors.toList());
        return list(createSql("list.permissions.info.by.user.id.and.module.ids")
                                                                                .setParam(USER_ID, id)
                                                                                .setVar("moduleIds", Strings.join(",", ids)),
                    Permission.class);
    }

    public List<Module> modules(long id) {
        return directPermissionInfo(id).getModules().stream().map(ModuleInfo::toModule).collect(Collectors.toList());
    }

    /**
     * @param id
     * @param actionInfos
     * @return
     */
    public boolean grant(long id, List<String> actionInfos) {
        userPermissionService.clear(Cnd.where(UserPermission.Fields.userId, "=", id));
        return Optional.ofNullable(dao().insert(actionInfos.stream().map(info -> {
            String moduleKey = info.substring(0, info.indexOf('.'));
            String actionKey = info.substring(info.indexOf('.') + 1);
            return UserPermission.builder()
                                 .userId(id)
                                 .actionKey(actionKey)
                                 .moduleKey(moduleKey)
                                 .moduleId(Optional.ofNullable(moduleService.fetch(moduleKey)).orElse(Module.builder().build()).getId())
                                 .build();
        }).collect(Collectors.toList()))).orElse(Lang.list()).size() == actionInfos.size();
    }

    /**
     * @param userId
     * @return
     */
    public List<RoleInfo> rolePoweredInfoByUserId(long userId) {
        return list(createSql("get.role.infos.by.user.id")
                                                          .setParam(USER_ID, userId),
                    RoleInfo.class);
    }

    /**
     * @param id
     * @param roles
     * @return
     */
    public boolean grantRole(long id, List<Long> roles) {
        userRoleService.clear(Cnd.where(UserRole.Fields.userId, "=", id));
        return Optional.ofNullable(dao()
                                        .insert(roles.stream()
                                                     .map(item -> UserRole.builder()
                                                                          .userId(id)
                                                                          .roleId(item)
                                                                          .build())
                                                     .collect(Collectors.toList())))
                       .orElse(Lang.list())
                       .size() == roles.size();
    }

}
