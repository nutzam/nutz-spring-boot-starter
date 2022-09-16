package tech.riemann.nutz.demo.config.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.lang.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.config.auth.JwtTokenUtil;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@RequiredArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    static final String AUTHORIZATION_KEY = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(httpServletRequest);
        if (Strings.isNotBlank(token)) {
            String username = JwtTokenUtil.getName(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_KEY);
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie ele : cookies) {
                    if (Strings.equalsIgnoreCase(ele.getName(), AUTHORIZATION_KEY)) {
                        token = ele.getValue();
                    }
                }
            }
        }
        if (token == null) {
            token = request.getParameter(AUTHORIZATION_KEY);
        }
        return token;
    }
}
