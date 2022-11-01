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
     * 获取角色权限
     * 
     * @param key
     *            角色key
     * @return 权限列表
     */
    public List<Permission> permissionsByRoleKey(String key) {
        return permissionInfosByKey(key).stream().filter(PermissionInfo::isSelected).collect(Collectors.toList());
    }

    /**
     * 获取角色权限信息
     * 
     * @param key
     *            角色key
     * @return 授权信息列表
     */
    public List<PermissionInfo> permissionInfosByKey(String key) {
        return list(sql("list.permissions.by.role.key")
                                                       .setParam("roleKey", key),
                    PermissionInfo.class);
    }

    /**
     * 为角色授权
     * 
     * @param key
     *            角色key
     * @param permissions
     *            权限列表 (权限keyPath列表)
     * @return 是否授权成功
     */
    public boolean grant(String key, List<String> permissions) {
        rolePermissionService.clear(Cnd.where(RolePermission::getRoleKey, EQ, key));
        return rolePermissionService.insert(permissions.stream()
                                                       .map(p -> RolePermission.builder().roleKey(key).permissionKeyPath(p).build())
                                                       .collect(Collectors.toList()))
                                    .size() == permissions.size();
    }
}
