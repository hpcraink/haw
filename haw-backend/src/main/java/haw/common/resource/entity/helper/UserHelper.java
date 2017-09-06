package haw.common.resource.entity.helper;

import haw.common.resource.entity.Login;
import haw.common.resource.type.Role;
import haw.common.security.UserPrincipal;
import java.security.Principal;

/**
 * The Class UserHelper. This class provides helper methods for checking user
 * related things.
 */
public abstract class UserHelper {

    /**
     * Checks if the given user has the given role.
     *
     * @param principal the user principal
     * @param role the role to check
     * @return true, if this user is in role
     */
    public static boolean isUserInRole(UserPrincipal principal, Role role) {
        return isUserInRole(principal, role.name());
    }

    /**
     * Checks if the given user has the given role.
     *
     * @param principal the user principal
     * @param role the role to check
     * @return true, if this user is in role
     */
    public static boolean isUserInRole(UserPrincipal principal, String role) {
        return principal != null && principal.isUserInRole(role);
    }

    /**
     * Checks if the given user is the logged in user.
     *
     * @param principal the user principal
     * @param credentials the log in credentials
     * @return true, if the given user is the logged in user.
     */
    public static boolean isThisUser(Principal principal, Login credentials) {
        return (credentials != null && isThisUser(principal, credentials.getPerson().getEmail()));
    }

    /**
     * Checks if the given user has the given email.
     *
     * @param principal the user principal
     * @param email the email
     * @return true, if the user has the given email.
     */
    public static boolean isThisUser(Principal principal, String email) {
        return (principal != null && principal.getName().equals(email));
    }

}
