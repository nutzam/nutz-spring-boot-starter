package club.zhcs.nutz.demo.config.auth;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.nutz.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import club.zhcs.auth.AbstractAuthService;
import club.zhcs.auth.AuthException;
import club.zhcs.auth.AuthUser;
import club.zhcs.auth.PasswordUtils;
import club.zhcs.nutz.demo.entity.acl.User;
import club.zhcs.nutz.demo.service.acl.UserService;

/**
 * @author kerbores(kerbores@gmail.com)
 */
@Component
public class AuthService extends AbstractAuthService {

    @Autowired
    UserService userService;

    LoadingCache<String, AuthUser> cache = CacheBuilder.newBuilder()
                                                       .concurrencyLevel(8)
                                                       .expireAfterAccess(10, TimeUnit.MINUTES)
                                                       .initialCapacity(10)
                                                       .maximumSize(300)
                                                       .recordStats()
                                                       .build(new CacheLoader<String, AuthUser>() {

                                                           @Override
                                                           public AuthUser load(String key) throws Exception {
                                                               AuthUser user = Optional.ofNullable(userService.fetch(key))
                                                                                       .orElseThrow(AuthException::new)
                                                                                       .toUser();
                                                               user.setPermissions(userService.permissions(key));
                                                               user.setRoles(userService.roles(key));
                                                               return user;
                                                           }
                                                       });

    /**
     * @param request
     */
    @Autowired
    public AuthService(HttpServletRequest request) {
        super(request);
    }

    @Override
    public List<String> roles() {
        return user().getRoles();
    }

    @Override
    public List<String> permissions() {
        return user().getPermissions();
    }

    @Override
    public AuthUser user() {
        try {
            return cache.get(Optional.ofNullable(userName()).orElseThrow(AuthException::new));
        }
        catch (ExecutionException e) {
            throw new AuthException();
        }
    }

    @Override
    public AuthUser login(LoginDto loginDto) {
        User user = userService.fetch(loginDto.getName());
        if (user == null) {
            throw new AuthException();
        }
        if (Strings.equals(user.getPwd(), PasswordUtils.encode(loginDto.getPassword(), loginDto.getName()))) {
            try {
                return cache.get(loginDto.getName());
            }
            catch (ExecutionException e) {
                throw new AuthException();
            }
        }
        throw new AuthException();
    }

}
