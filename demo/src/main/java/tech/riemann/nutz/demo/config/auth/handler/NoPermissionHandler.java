package tech.riemann.nutz.demo.config.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import tech.riemann.nutz.demo.dto.response.GlobalError;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 */
public class NoPermissionHandler implements AccessDeniedHandler {

    /**
     * @param request
     * @param response
     * @param accessDeniedException
     * @throws IOException
     * @throws ServletException
     * @see org.springframework.security.web.access.AccessDeniedHandler#handle(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse,
     *      org.springframework.security.access.AccessDeniedException)
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        Json.toJson(response.getWriter(), GlobalError.builder().code(HttpStatus.FORBIDDEN.value()).message(exception.getLocalizedMessage()).build());
    }

}
