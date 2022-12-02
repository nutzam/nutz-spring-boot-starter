package tech.riemann.nutz.demo.config.auth.handler;

import java.io.IOException;

import org.nutz.json.Json;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.riemann.nutz.demo.dto.response.GlobalError;

/**
 * @author kerbores(kerbores@riemann.tech)
 *
 */
public class LoginFailureHandler implements AuthenticationFailureHandler{

    /**
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(jakarta.servlet.http.HttpServletRequest, jakarta.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Json.toJson(response.getWriter(), GlobalError.builder().code(HttpStatus.UNAUTHORIZED.value()).message(exception.getLocalizedMessage()).build());
    }

}
