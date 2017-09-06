package haw.application.rest.security.model.context;

import haw.common.resource.entity.Person;
import haw.common.security.UserContext;
import haw.common.security.UserPrincipal;

import javax.ws.rs.core.SecurityContext;

/**
 * Security Context for authenticated users (Members of JAK).
 * 
 * @author Uwe Eisele
 */
public class UserSecurityContext implements SecurityContext {

	/** The user context. */
	private final UserContext userContext;

	/** The is secure. */
	private final boolean isSecure;

	/** The authentication scheme. */
	private final String authenticationScheme;

	/**
	 * Instantiates a new user security context.
	 * 
	 * @param userContext
	 *            the user context
	 * @param isSecure
	 *            the is secure
	 * @param authenticationScheme
	 *            the authentication scheme
	 */
	public UserSecurityContext(UserContext userContext, boolean isSecure, String authenticationScheme) {
		this.userContext = userContext;
		this.isSecure = isSecure;
		this.authenticationScheme = authenticationScheme;
	}

	/**
	 * Returns the corresponding member object of this user.
	 * 
	 * @return the member
	 */
	public Person getPerson() {
		return userContext.getPerson();
	}

	/**
	 * Returns the principal object of the current authenticated user. This
	 * principal contains the corresponding member object.
	 * 
	 * @return the user principal
	 */
	@Override
	public UserPrincipal getUserPrincipal() {
		return userContext.getPrincipal();
	}

	/**
	 * Returns a boolean indicating whether the authenticated user is included
	 * in the specified logical "role".
	 * If the user has not been authenticated, the method returns false.
	 * 
	 * @param role
	 *            a String specifying the name of the role
	 * @return true, if is user in role
	 */
	@Override
	public boolean isUserInRole(String role) {
		return userContext.getPrincipal().isUserInRole(role);
	}

	/**
	 * Returns a boolean indicating whether this request was made using a secure
	 * channel, such as HTTPS.
	 * 
	 * @return true, if is secure
	 */
	@Override
	public boolean isSecure() {
		return isSecure;
	}

	/**
	 * Returns the string value of the authentication scheme used to protect the resource.
	 * If the resource is not authenticated, null is returned. Values are the same as the CGI variable AUTH_TYPE
	 * @return one of the static members BASIC_AUTH, FORM_AUTH, CLIENT_CERT_AUTH, DIGEST_AUTH (suitable for == comparison)
	 * or the container-specific string indicating the authentication scheme, or null if the request was not authenticated.
	 */
	@Override
	public String getAuthenticationScheme() {
		return authenticationScheme;
	}

}
