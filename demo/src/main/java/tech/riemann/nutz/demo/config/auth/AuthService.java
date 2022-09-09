package tech.riemann.nutz.demo.config.auth;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import club.zhcs.auth.AbstractAuthService;
import club.zhcs.auth.AuthException;
import club.zhcs.auth.AuthUser;
import club.zhcs.auth.PasswordUtils;
import tech.riemann.nutz.demo.entity.acl.User;
import tech.riemann.nutz.demo.service.acl.UserService;

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

                                                               User user = Optional.ofNullable(userService.fetch(key))
                                                                                   .orElseThrow(AuthException::new);

                                                               return AuthUser.builder()
                                                                              .userName(user.getName())
                                                                              .permissions(userService.permissions(key))
                                                                              .roles(userService.roles(key))
                                                                              .password("nutz")
                                                                              .build()
                                                                              .addExt("user", user)
                                                                              .token();
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
        if (PasswordUtils.randomSaltVerify(loginDto.getPassword(), user.getPassword())) {
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
