package agh.edu.pl.depressiondetectorbackend.security;

import agh.edu.pl.depressiondetectorbackend.domain.User;
import agh.edu.pl.depressiondetectorbackend.domain.UserPrivilege;
import agh.edu.pl.depressiondetectorbackend.domain.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    TokenAuthorizationFilter(final AuthenticationManager authManager,
                             final UserRepository userRepository,
                             BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(authManager);
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest req,
                                    final HttpServletResponse res,
                                    final FilterChain chain) throws IOException, ServletException {
        final String username = req.getHeader("username");
        final String password = req.getHeader("password");
        if (username != null && password != null) {
            try {
                this.setAuthentication(username, password);
            } catch (AuthException e) {
                res.sendError(e.getStatus().value(), e.getReason());
                return;
            }
        }
        chain.doFilter(req, res);
    }

    private void setAuthentication(final String username, final String password) throws AuthException {
        final Authentication authentication = this.getAuthentication(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Authentication getAuthentication(final String username, final String password) throws AuthException {
        User user = userRepository.findByUsername(username).orElseThrow(AuthException::new);
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new AuthException();
        }
        final List<UserPrivilege> privileges = new ArrayList<>(user.getUserPrivileges());
        return new UsernamePasswordAuthenticationToken(user, user, privileges);
    }
}
