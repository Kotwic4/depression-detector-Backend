package agh.edu.pl.depressiondetectorbackend.domain.user;

import agh.edu.pl.depressiondetectorbackend.security.AuthorizationRoles;
import org.springframework.security.core.GrantedAuthority;

/**
 * Privileges which {@link User user} can have in system.
 */
public enum UserPrivilege implements GrantedAuthority {

    /**
     * Enable access to admin panel.
     */
    ADMIN_PANEL(AuthorizationRoles.ADMIN_PANEL_TEXT),
    /**
     * Default privilege.
     */
    USER_PANEL(AuthorizationRoles.USER_PANEL_TEXT);

    public final String name;

    UserPrivilege(String name){
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name;
    }
}