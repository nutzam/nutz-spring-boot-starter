package tech.riemann.nutz.demo.config.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 */
public class LogoutHandler implements LogoutSuccessHandler {

    /**
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     * @see org.springframework.security.web.authentication.logout.LogoutSuccessHandler#onLogoutSuccess(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse,
     *      org.springframework.security.core.Authentication)
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());
    }

}
