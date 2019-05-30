package agh.edu.pl.depressiondetectorbackend.security;

/**
 * User authorization roles strings.
 * The role string is created to eliminate boilerplate of PreAuthorize.
 */
public class AuthorizationRoles {
    public final static String USER_PANEL_TEXT = "USER_PANEL";
    public final static String USER_PANEL_ROLE = "hasRole('" + USER_PANEL_TEXT + "')";

    public final static String ADMIN_PANEL_TEXT = "ADMIN_PANEL";
    public final static String ADMIN_PANEL_ROLE = "hasRole('" + ADMIN_PANEL_TEXT + "')";
}
