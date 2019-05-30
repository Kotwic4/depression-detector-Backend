package agh.edu.pl.depressiondetectorbackend.security;


import agh.edu.pl.depressiondetectorbackend.domain.auth.SessionRepository;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Defines request security.
 *
 * By authorized routes, one mean that request on this routes needs valid token.
 * If route is unauthorized, request don't have to provide token.
 * However, if they do, it must be a valid token.
 *
 * "/api/**" are authorized
 * "/api/registration/**" and
 * "/api/authentication/**"
 * are exception of a rule and are unauthorized.
 *
 * All other routes are unauthorized.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final SessionRepository sessionRepository;

    public WebSecurity(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/api/registration/**").permitAll()
                .antMatchers("/api/authentication/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .addFilter(new TokenAuthorizationFilter(authenticationManager(), sessionRepository))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}