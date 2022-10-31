package tech.riemann.nutz.demo.service.acl;

import java.util.List;
import java.util.stream.Collectors;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.spring.boot.service.interfaces.IdNameEntityService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.dto.response.PermissionInfo;
import tech.riemann.nutz.demo.entity.acl.Permission;
import tech.riemann.nutz.demo.entity.acl.Role;
import tech.riemann.nutz.demo.entity.acl.RolePermission;

/**
 * 角色 服务类
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-09 23:38:21
 */
@Service
@RequiredArgsConstructor
public class RoleService implements IdNameEntityService<Role> {

    private final Dao dao;
    private final RolePermissionService rolePermissionService;

    /**
     * @return
     * @see org.nutz.spring.boot.service.ExtService#dao()
     */
    @Override
    public Dao dao() {
        return dao;
    }

    /**
     * @param key
     * @return
     */
    public List<Permission> permissionsByRoleKey(String key) {
        return permissionInfosByKey(key).stream().filter(PermissionInfo::isSelected).collect(Collectors.toList());
    }

    /**
     * @param key
     * @return
     */
    public List<PermissionInfo> permissionInfosByKey(String key) {
        return list(sql("list.permissions.by.role.key")
                                                       .setParam("roleKey", key),
                    PermissionInfo.class);
    }

    /**
     * @param key
     * @param permissions
     * @return
     */
    public boolean grant(String key, List<String> permissions) {
        rolePermissionService.clear(Cnd.where(RolePermission::getRoleKey, EQ, key));
        return rolePermissionService.insert(permissions.stream()
                                                       .map(p -> RolePermission.builder().roleKey(key).permissionKeyPath(p).build())
                                                       .collect(Collectors.toList()))
                                    .size() == permissions.size();
    }
}
