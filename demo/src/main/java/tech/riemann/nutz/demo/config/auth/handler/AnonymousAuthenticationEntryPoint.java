package tech.riemann.nutz.demo.config.auth.handler;

import java.io.IOException;

import org.nutz.json.Json;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.riemann.nutz.demo.dto.response.GlobalError;

/**
 * 
 * @author kerbores(kerbores@riemann.tech)
 *
 */
public class AnonymousAuthenticationEntryPoint implements AuthenticationEntryPoint{

    /**
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     * @see org.springframework.security.web.AuthenticationEntryPoint#commence(jakarta.servlet.http.HttpServletRequest, jakarta.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        Json.toJson(response.getWriter(), GlobalError.builder().code(HttpStatus.FORBIDDEN.value()).message(authException.getLocalizedMessage()).build());
    }

}
