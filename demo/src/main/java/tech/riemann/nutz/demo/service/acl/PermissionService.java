package tech.riemann.nutz.demo.service.acl;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.spring.boot.service.interfaces.IdEntityService;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.dto.response.PermissionTree;
import tech.riemann.nutz.demo.entity.acl.Permission;
import tech.riemann.nutz.demo.utils.Tree;
import tech.riemann.nutz.demo.utils.Treeable;

/**
 * 权限 服务类
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-10-31 16:45:15
 */
@Service
@RequiredArgsConstructor
public class PermissionService implements IdEntityService<Permission> {

    private final Dao dao;

    LoadingCache<String, List<Permission>> cache = CacheBuilder.newBuilder()
                                                               .concurrencyLevel(8)
                                                               .expireAfterAccess(10, TimeUnit.MINUTES)
                                                               .initialCapacity(10)
                                                               .maximumSize(300)
                                                               .recordStats()
                                                               .build(new CacheLoader<String, List<Permission>>() {

                                                                   @Override
                                                                   public List<Permission> load(String clientId) throws Exception {
                                                                       return all();
                                                                   }
                                                               });

    /**
     * @return
     * @see org.nutz.spring.boot.service.ExtService#dao()
     */
    @Override
    public Dao dao() {
        return dao;
    }

    /**
     * @param force
     * @return
     */
    public List<Tree<String>> permissionTree(boolean force) {
        List<Permission> permissions = permissions(force);
        List<Treeable<String>> permissionTrees = permissions.stream()
                                                            .map(PermissionTree::build)
                                                            .collect(Collectors.toList());
        return Tree.from(permissionTrees,
                         permissions.stream()
                                    .filter(organization -> Strings.isBlank(organization.getParentKey()))
                                    .map(Permission::getKey)
                                    .toList());
    }

    /**
     * @param force
     * @return
     */
    public List<Permission> permissions(boolean force) {
        try {
            if (force) {
                cache.refresh("all");
            }
            return cache.get("all");
        }
        catch (ExecutionException e) {
            throw Lang.wrapThrow(e);
        }
    }

    /**
     * @param permissions
     */
    public void batchAddPermissions(List<Permission> permissions) {
        clear();
        insert(permissions);
    }

    /**
     * @param permissions
     */
    public void updatePermissions(List<Permission> permissions) {
        permissions.stream().forEach(permission -> {
            if (count(Cnd.where(Permission::getKeyPath, EQ, permission.getKeyPath())) > 0) {
                update(permission,
                       Cnd.where(Permission::getKeyPath, EQ, permission.getKeyPath()),
                       Permission.Fields.name,
                       Permission.Fields.description,
                       Permission.Fields.type);
            } else {
                insert(permission);
            }
        });
    }
}
