package tech.riemann.nutz.demo.config.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import tech.riemann.nutz.demo.config.auth.JwtUser;
import tech.riemann.nutz.demo.dto.response.LoginUser;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse,
     *      org.springframework.security.core.Authentication)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Json.toJson(response.getWriter(), LoginUser.from((JwtUser) authentication.getPrincipal()));
    }

}
