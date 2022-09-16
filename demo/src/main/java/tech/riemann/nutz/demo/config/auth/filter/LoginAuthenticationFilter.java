package tech.riemann.nutz.demo.config.auth.filter;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import club.zhcs.auth.AuthService.LoginDto;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 */
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            UsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream is = request.getInputStream()) {
                LoginDto loginDto = Json.fromJson(LoginDto.class, Streams.utf8r(is));
                authRequest = new UsernamePasswordAuthenticationToken(
                                                                      loginDto.getName(),
                                                                      loginDto.getPassword());
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
            catch (IOException e) {
                throw Lang.wrapThrow(e, AuthenticationException.class);
            }
        } else {
            return super.attemptAuthentication(request, response);
        }
    }
}
