package tech.riemann.nutz.demo.config.auth;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import tech.riemann.nutz.demo.entity.acl.User;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@Data
@SuperBuilder
public class JwtUser implements UserDetails {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private User user;
    private List<String> roles;
    private List<String> permissions;

    /**
     * @return
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @return
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @return
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @return
     * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * @return
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream()
                         .map(SimpleGrantedAuthority::new)
                         .collect(Collectors.toList());
    }

    /**
     * @return
     * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
     */
    @Override
    public String getPassword() {
        return getUser().getPassword();
    }

    /**
     * @return
     * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
     */
    @Override
    public String getUsername() {
        return getUser().getName();
    }
}
