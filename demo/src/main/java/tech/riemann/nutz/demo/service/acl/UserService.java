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
import tech.riemann.nutz.demo.dto.response.MenuInfo;
import tech.riemann.nutz.demo.dto.response.PermissionInfo;
import tech.riemann.nutz.demo.dto.response.RoleInfo;
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

    private final UserPermissionService userPermissionService;

    private final UserRoleService userRoleService;

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
     * @param name
     * @return
     */
    public List<String> permissions(String name) {
        return permissionInfosByUserName(name).stream()
                                              .filter(PermissionInfo::isSelected)
                                              .map(item -> String.format("%s.%s", item.getMenuKey(), item.getKey()))
                                              .collect(Collectors.toList());
    }

    /**
     * @param name
     * @return
     */
    public List<String> roles(String name) {
        return roleInfosByUserName(name).stream()
                                        .filter(RoleInfo::isSelected)
                                        .map(RoleInfo::getKey)
                                        .collect(Collectors.toList());
    }

    /**
     * @param name
     * @return
     */
    public String resetPassword(String name) {
        String newPassword = R.sg(10).next();
        if (update(Chain.make(User.Fields.password, PasswordUtils.randomSaltEncode(newPassword)), Cnd.where(User::getName, EntityService.EQ, name)) != 1) {
            throw BizException.create("重置密码失败!");
        }
        return newPassword;
    }

    /**
     * @param name
     * @return
     */
    public List<MenuInfo> permissionsByUserName(String name) {
        return MenuInfo.from(permissionInfosByUserName(name));
    }

    /**
     * @param name
     * @return
     */
    public List<PermissionInfo> permissionInfosByUserName(String name) {
        List<PermissionInfo> directPermissionInfos = directPermissionInfosByUserName(name);
        List<PermissionInfo> indirectPermissionInfos = indirectPermissionInfosByUserName(name);
        directPermissionInfos.stream().forEach(direct -> {
            boolean selected = indirectPermissionInfos.stream()
                                                      .anyMatch(indirect -> Strings.equals(indirect.getKey(), direct.getKey())
                                                                            && Strings.equals(indirect.getMenuKey(), direct.getMenuKey())
                                                                            && indirect.isSelected());
            if (selected) {
                direct.setSelected(selected);
            }
        });
        return directPermissionInfos;
    }

    public List<PermissionInfo> directPermissionInfosByUserName(String name) {
        return list(sql("list.direct.permissions.by.user.name")
                                                               .setParam(USER_NAME, name),
                    PermissionInfo.class);
    }

    public List<PermissionInfo> indirectPermissionInfosByUserName(String name) {
        return list(sql("list.indirect.permissions.by.user.name")
                                                                 .setParam(USER_NAME, name),
                    PermissionInfo.class);
    }

    /**
     * @param name
     * @param permissions
     * @return
     */
    public boolean grant(String name, List<String> permissions) {
        userPermissionService.clear(Cnd.where(UserPermission::getUserName, EntityService.EQ, name));
        return userPermissionService.insert(permissions.stream().map(item -> {
            String[] infos = item.split("\\.");
            return UserPermission.builder()
                                 .userName(name)
                                 .menuKey(infos[0])
                                 .buttonKey(infos[1])
                                 .build();
        }).collect(Collectors.toList())).size() == permissions.size();
    }

    /**
     * @param name
     * @return
     */
    public List<RoleInfo> roleInfosByUserName(String name) {
        return list(sql("list.role.infos.by.user.name")
                                                       .setParam(USER_NAME, name),
                    RoleInfo.class);
    }

    /**
     * @param name
     * @param roles
     * @return
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
