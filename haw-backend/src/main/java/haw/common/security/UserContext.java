package haw.common.security;

import haw.common.resource.entity.Person;
import haw.common.resource.type.Role;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.enterprise.context.RequestScoped;

/**
 * Context for a user.
 *
 * @author Uwe Eisele, Rainer Keller
 */
@RequestScoped
public class UserContext {

    /**
     * The Constant ANONYMOUS_USER.
     */
    private final static String ANONYMOUS_USER = "anonymous";

    /**
     * The authenticated person.
     */
    private Person person;

    /**
     * The corresponding principal.
     */
    private UserPrincipal principal;

    /**
     * Instantiates a context for a anonymous user with no roles.
     */
    public UserContext() {
        this(ANONYMOUS_USER, new HashSet<>(Arrays.asList(Role.USER)));
    }

    /**
     * Instantiates a user context for a member.
     *
     * @param person Corresponding person object of the user
     */
    public UserContext(Person person) {
        setPerson(person);
    }

    /**
     * Instantiates a user context for a not authenticated user (not a member).
     *
     * @param username the username
     * @param roles the roles
     */
    public UserContext(String username, Collection<Role> roles) {
        this.person = null;
        this.principal = new UserPrincipal(username, roles);
    }

    /**
     * Returns the corresponding person object of this user.
     *
     * @return Person object if user is authenticated, or null if not
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Initializes this context with a person. After this the context is
     * authenticated. This can be done only once.
     *
     * @param person the new person
     */
    public void setPerson(Person person) {
        if (this.person != null) {
            throw new IllegalStateException("User context can only be initialized once.");
        }
        if (person == null || person.getLogin() == null) {
            throw new IllegalArgumentException(
                    "Member or credentials of member can not be null.");
        }
        if (person.getEmail() == null
                || person.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Person must have an email.");
        }

        this.person = person;
        Collection<Role> roles = (person.getRoles() != null ? person.getRoles()
                : new HashSet<Role>());
        this.principal = new UserPrincipal(person.getEmail(), roles);
    }

    /**
     * Indicates weather this user is a member. If a user is a member, then the
     * user is authenticated.
     *
     * @return True if authenticated as a member, false if not.
     */
    public boolean isAuthenticatedMember() {
        return getPerson() != null;
    }

    /**
     * Returns the principal of the user.
     *
     * @return the principal
     */
    public UserPrincipal getPrincipal() {
        return principal;
    }

}
