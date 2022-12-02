package tech.riemann.nutz.demo.config.auth.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author kerbores(kerbores@riemann.tech)
 *
 */
public class LogoutHandler implements LogoutSuccessHandler {

    /**
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     * @see org.springframework.security.web.authentication.logout.LogoutSuccessHandler#onLogoutSuccess(jakarta.servlet.http.HttpServletRequest,
     *      jakarta.servlet.http.HttpServletResponse,
     *      org.springframework.security.core.Authentication)
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());
    }

}
