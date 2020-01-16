package club.zhcs.nutz.demo.service.acl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.spring.boot.service.IdNameBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import club.zhcs.nutz.demo.dto.response.ModuleInfo;
import club.zhcs.nutz.demo.dto.response.Permission;
import club.zhcs.nutz.demo.dto.response.PermissionInfo;
import club.zhcs.nutz.demo.entity.acl.Module;
import club.zhcs.nutz.demo.entity.acl.Role;
import club.zhcs.nutz.demo.entity.acl.RolePermission;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@Service
public class RoleService extends IdNameBaseService<Role> {

    @Autowired
    RolePermissionService rolePermissionService;

    @Autowired
    ModuleService moduleService;

    static final String ROLE_ID = "roleId";

    /**
     * - 全部角色
     * 
     * @param userId
     *            用户id
     * @return 角色名列表
     */
    public List<String> roleInfos(long userId) {
        return roles(userId).stream()
                            .map(Role::getKey)
                            .collect(Collectors.toList());
    }

    /**
     * -全部角色
     * 
     * @param userId
     *            用户id
     * @return 角色列表
     */
    public List<Role> roles(long userId) {
        Sql sql = createSql("list.all.roles.by.user.id");
        sql.params().set("userId", userId);
        return list(sql);
    }

    public PermissionInfo actionInfo(Long id, Long... moduleIds) {
        return toPermissionInfo(actions(id, moduleIds));
    }

    public List<Permission> actions(Long id, Long... moduleIds) {
        List<Long> ids = Stream.of(modules(id).stream().map(Module::getId).collect(Collectors.toList()), Lang.list(moduleIds))
                               .flatMap(Collection::stream)
                               .distinct()
                               .collect(Collectors.toList());
        Sql sql = createSql("list.permissions.info.by.role.id.and.module.ids");
        sql.params().set(ROLE_ID, id);
        sql.vars().set("moduleIds", Strings.join(",", ids));
        sql.setCallback(Sqls.callback.entities());
        sql.setEntity(dao().getEntity(Permission.class));
        dao().execute(sql);
        return sql.getList(Permission.class);
    }

    public List<Module> modules(long id) {
        return permissionInfo(id).getModules().stream().map(ModuleInfo::toModule).collect(Collectors.toList());
    }

    /**
     * @param id
     * @return
     */
    public PermissionInfo permissionInfo(long id) {
        return toPermissionInfo(permissions(id));
    }

    public List<Permission> permissions(long roleId) {
        return permissions("list.permissions.by.role.id", roleId);
    }

    /**
     * 查询权限
     * 
     * @param sqlKey
     *            sqlkey
     * @param roleId
     *            角色id
     * @return 权限列表
     */
    public List<Permission> permissions(String sqlKey, long roleId) {
        Sql sql = createSql(sqlKey);
        sql.params().set(ROLE_ID, roleId);
        sql.setCallback(Sqls.callback.entities());
        sql.setEntity(dao().getEntity(Permission.class));
        dao().execute(sql);
        return sql.getList(Permission.class);
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

    /**
     * @param id
     * @param actionInfos
     * @return
     */
    public boolean grant(long id, List<String> actionInfos) {
        rolePermissionService.clear(Cnd.where(ROLE_ID, "=", id));
        return Optional.ofNullable(dao().insert(actionInfos.stream().map(info -> {
            String moduleKey = info.substring(0, info.indexOf('.'));
            String actionKey = info.substring(info.indexOf('.') + 1);
            return RolePermission.builder()
                                 .roleId(id)
                                 .actionKey(actionKey)
                                 .moduleKey(moduleKey)
                                 .moduleId(Optional.ofNullable(moduleService.fetch(moduleKey)).orElse(Module.builder().build()).getId())
                                 .build();
        }).collect(Collectors.toList()))).orElse(Lang.list()).size() == actionInfos.size();
    }

}
