package tech.riemann.nutz.demo.config.auth;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.nutz.lang.Lang;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.entity.acl.User;
import tech.riemann.nutz.demo.service.acl.UserService;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    LoadingCache<String, JwtUser> cache = CacheBuilder.newBuilder()
                                                      .concurrencyLevel(8)
                                                      .expireAfterAccess(10, TimeUnit.MINUTES)
                                                      .initialCapacity(10)
                                                      .maximumSize(300)
                                                      .recordStats()
                                                      .build(new CacheLoader<String, JwtUser>() {

                                                          @Override
                                                          public JwtUser load(String username) throws Exception {

                                                              User user = userService.fetch(username);
                                                              if (user == null) {
                                                                  throw new UsernameNotFoundException(String.format("'%s'.这个用户不存在", username));
                                                              }
                                                              return JwtUser.builder()
                                                                            .user(user)
                                                                            .roles(userService.roles(username))
                                                                            .permissions(userService.permissions(username))
                                                                            .build();
                                                          }
                                                      });

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            return cache.get(username);
        }
        catch (ExecutionException e) {
            throw Lang.wrapThrow(e);
        }
    }

}
