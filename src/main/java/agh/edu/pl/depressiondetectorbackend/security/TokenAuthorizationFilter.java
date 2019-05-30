package agh.edu.pl.depressiondetectorbackend.security;

import agh.edu.pl.depressiondetectorbackend.domain.auth.AuthException;
import agh.edu.pl.depressiondetectorbackend.domain.auth.Session;
import agh.edu.pl.depressiondetectorbackend.domain.auth.SessionRepository;
import agh.edu.pl.depressiondetectorbackend.domain.user.User;
import agh.edu.pl.depressiondetectorbackend.domain.user.UserPrivilege;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Filter, that check if there is a authorization token in request and validate it.
 * <ul>
 * <li>If there is no token, filter pass request to chain.</li>
 * <li>
 * If there is token is valid, the authorization of request is set.
 * Filter pass request to chain.
 * </li>
 * <li>
 * If there is token, but it is not valid or outdated, there is a an exception thrown.
 * </li>
 * </ul>
 */
public class TokenAuthorizationFilter extends BasicAuthenticationFilter {

    private final SessionRepository sessionRepository;

    TokenAuthorizationFilter(final AuthenticationManager authManager, SessionRepository sessionRepository) {
        super(authManager);
        this.sessionRepository = sessionRepository;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest req,
                                    final HttpServletResponse res,
                                    final FilterChain chain) throws IOException, ServletException {
        final String token = req.getHeader("token");
        if (token != null) {
            try {
                this.setAuthentication(token);
            } catch (AuthException e) {
                res.sendError(e.getStatus().value(), e.getReason());
                return;
            }
        }
        chain.doFilter(req, res);
    }

    private void setAuthentication(final String token) throws AuthException {
        final Authentication authentication = this.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Authentication getAuthentication(final String token) throws AuthException {
        Session session = sessionRepository.findByToken(token).orElseThrow(AuthException::new);
        User user = session.getUser();
        final List<UserPrivilege> privileges = new ArrayList<>(user.getUserPrivileges());
        return new UsernamePasswordAuthenticationToken(user, user, privileges);
    }
}
