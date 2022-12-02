package tech.riemann.nutz.demo.config.auth.handler;

import java.io.IOException;

import org.nutz.json.Json;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.riemann.nutz.demo.dto.response.GlobalError;

/**
 * @author kerbores(kerbores@riemann.tech)
 *
 */
public class NoPermissionHandler implements AccessDeniedHandler {

    /**
     * @param request
     * @param response
     * @param accessDeniedException
     * @throws IOException
     * @throws ServletException
     * @see org.springframework.security.web.access.AccessDeniedHandler#handle(jakarta.servlet.http.HttpServletRequest, jakarta.servlet.http.HttpServletResponse, org.springframework.security.access.AccessDeniedException)
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        Json.toJson(response.getWriter(), GlobalError.builder().code(HttpStatus.FORBIDDEN.value()).message(accessDeniedException.getLocalizedMessage()).build());
    }

}
