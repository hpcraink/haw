package haw.common.security;

import haw.common.resource.type.Role;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Principal for authenticated users (Members of JAK).
 * 
 * @author Uwe Eisele
 */
public class UserPrincipal implements Principal {

	/** The username. */
	private final String username;

	/** The roles. */
	private final SortedSet<Role> roles;

	/**
	 * Instantiates a new principal.
	 * 
	 * @param username
	 *            The name of the user
	 * @param roles
	 *            The roles of the user
	 */
	public UserPrincipal(String username, Collection<Role> roles) {
		this.username = username;
		this.roles = Collections.unmodifiableSortedSet(new TreeSet<>(roles));
	}

	/**
	 * Returns the username of this principal.
	 * 
	 * @return the name
	 */
	@Override
	public String getName() {
		return username;
	}

	/**
	 * Returns the username of this principal.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the roles of the principal.
	 * 
	 * @return the roles
	 */
	public SortedSet<Role> getRoles() {
		return roles;
	}

	/**
	 * Returns the role of the user which has the highest priority.
	 * 
	 * @return the highest role of user
	 */
	public Role getHighestRoleOfUser() {
		return roles.last();
	}

	/**
	 * Indicates weather this principal is in the given role or not.
	 * 
	 * @param role
	 *            the role
	 * @return True if principal is in given role, false if not
	 */
	public boolean isUserInRole(String role) {
		return isUserInRole(Role.getByName(role));
	}

	/**
	 * Indicates whether this principal is in the given role or not.
	 * 
	 * @param role
	 *            the role
	 * @return True if principal is in given role, false if not
	 */
	public boolean isUserInRole(Role role) {
		if (role != null && !getRoles().isEmpty()) {
			return getRoles().last().getPriority() >= role.getPriority();
		}
		return false;
	}

}
