package tech.riemann.nutz.demo.config.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import tech.riemann.nutz.demo.dto.response.GlobalError;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {

    /**
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse,
     *      org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Json.toJson(response.getWriter(), GlobalError.builder().code(HttpStatus.UNAUTHORIZED.value()).message(exception.getLocalizedMessage()).build());
    }

}
