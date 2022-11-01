package tech.riemann.nutz.demo.service.acl;

import java.util.List;
import java.util.stream.Collectors;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.lang.Strings;
import org.nutz.lang.random.R;
import org.nutz.spring.boot.service.interfaces.EntityService;
import org.nutz.spring.boot.service.interfaces.IdNameEntityService;
import org.springframework.stereotype.Service;

import club.zhcs.auth.PasswordUtils;
import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.dto.response.PermissionInfo;
import tech.riemann.nutz.demo.dto.response.RoleInfo;
import tech.riemann.nutz.demo.entity.acl.Permission;
import tech.riemann.nutz.demo.entity.acl.User;
import tech.riemann.nutz.demo.entity.acl.UserPermission;
import tech.riemann.nutz.demo.entity.acl.UserRole;
import tech.riemann.nutz.demo.exception.BizException;

/**
 * 用户 服务类
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-09 23:38:21
 */
@Service
@RequiredArgsConstructor
public class UserService implements IdNameEntityService<User> {

    private final Dao dao;

    private final UserRoleService userRoleService;

    private final UserPermissionService userPermissionService;

    static final String USER_NAME = "userName";

    /**
     * @return
     * @see org.nutz.spring.boot.service.ExtService#dao()
     */
    @Override
    public Dao dao() {
        return dao;
    }

    /**
     * 获取用户权限
     * 
     * @param name
     *            用户名
     * @return 权限字符串列表
     */
    public List<String> permissions(String name) {
        return permissionInfosByUserName(name).stream()
                                              .map(Permission::getKeyPath)
                                              .collect(Collectors.toList());
    }

    /**
     * 获取用户角色
     * 
     * @param name
     *            用户名
     * @return 角色字符串列表
     */
    public List<String> roles(String name) {
        return roleInfosByUserName(name).stream()
                                        .filter(RoleInfo::isSelected)
                                        .map(RoleInfo::getKey)
                                        .collect(Collectors.toList());
    }

    /**
     * 重置用户密码
     * 
     * @param name
     *            用户名
     * @return 新密码
     */
    public String resetPassword(String name) {
        String newPassword = R.sg(10).next();
        if (update(Chain.make(User.Fields.password, PasswordUtils.randomSaltEncode(newPassword)),
                   Cnd.where(User::getName, EntityService.EQ, name)) != 1) {
            throw BizException.create("重置密码失败!");
        }
        return newPassword;
    }

    /**
     * 获取用户权限
     * 
     * @param name
     *            用户名
     * @return 权限列表
     */
    public List<Permission> permissionsByUserName(String name) {
        return permissionInfosByUserName(name).stream().filter(PermissionInfo::isSelected).collect(Collectors.toList());
    }

    /**
     * 获取用户授权信息
     * 
     * @param name
     *            用户名
     * @return 授权信息列表
     */
    public List<PermissionInfo> permissionInfosByUserName(String name) {
        List<PermissionInfo> directPermissionInfos = directPermissionInfosByUserName(name);
        List<PermissionInfo> indirectPermissionInfos = indirectPermissionInfosByUserName(name);
        directPermissionInfos.stream().forEach(direct -> {
            boolean selected = indirectPermissionInfos.stream()
                                                      .anyMatch(indirect -> Strings.equals(indirect.getKeyPath(), direct.getKeyPath())
                                                                            && indirect.isSelected());
            if (selected) {
                direct.setSelected(selected);
            }
        });
        return directPermissionInfos;
    }

    /**
     * 获取用户直接权限(通过user_permission)
     * 
     * @param name
     *            用户名
     * @return 权限信息列表
     */
    public List<PermissionInfo> directPermissionInfosByUserName(String name) {
        return list(sql("list.direct.permissions.by.user.name")
                                                               .setParam(USER_NAME, name),
                    PermissionInfo.class);
    }

    /**
     * 获取用户间接权限(通过user_role -> role_permission)
     * 
     * @param name
     *            用户名
     * @return 权限信息列表
     */
    public List<PermissionInfo> indirectPermissionInfosByUserName(String name) {
        return list(sql("list.indirect.permissions.by.user.name")
                                                                 .setParam(USER_NAME, name),
                    PermissionInfo.class);
    }

    /**
     * 为用户授权
     * 
     * @param name
     *            用户名
     * @param permissions
     *            权限列表 (权限keyPath列表)
     * @return 是否授权成功
     */
    public boolean grant(String name, List<String> permissions) {
        userPermissionService.clear(Cnd.where(UserPermission::getUserName, EQ, name));
        return userPermissionService.insert(permissions.stream()
                                                       .map(p -> UserPermission.builder().permissionKeyPath(p).userName(name).build())
                                                       .collect(Collectors.toList()))
                                    .size() == permissions.size();
    }

    /**
     * 获取用户角色信息
     * 
     * @param name
     *            用户名
     * @return 角色信息列表
     */
    public List<RoleInfo> roleInfosByUserName(String name) {
        return list(sql("list.role.infos.by.user.name")
                                                       .setParam(USER_NAME, name),
                    RoleInfo.class);
    }

    /**
     * 为用户设置角色
     * 
     * @param name
     *            用户名
     * @param roles
     *            角色列表 (角色 key 列表)
     * @return 是否设置成功
     */
    public boolean grantRole(String name, List<String> roles) {
        userRoleService.clear(Cnd.where(UserRole::getUserName, EntityService.EQ, name));
        return userRoleService.insert(roles.stream()
                                           .map(role -> UserRole.builder()
                                                                .userName(name)
                                                                .roleKey(role)
                                                                .build())
                                           .collect(Collectors.toList()))
                              .size() == roles.size();
    }
}
