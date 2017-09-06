package haw.application.rest.security.filter;

import haw.application.rest.security.model.context.UserSecurityContext;
import haw.common.exception.HawAuthenticationException;
import haw.common.exception.HawException;
import haw.common.helper.BeanHelper;
import haw.common.resource.entity.Person;
import haw.common.security.UserContext;
import haw.module.security.service.AuthenticationService;

import java.io.IOException;

import javax.annotation.Priority;
import javax.naming.NamingException;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

/**
 * Filter in which authentication is done. This is achieved by creating a new
 * security context with the corresponding member. If no authorization string is
 * provided the user gets authenticated as an an anonymous user with no roles.
 *
 * @author Uwe Eisele
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class RequestAuthenticationFilter implements ContainerRequestFilter {

    /**
     * The authentication service.
     */
    private AuthenticationService authenticationService;

    /**
     * The user context.
     */
    private UserContext userContext;

    /**
     * Instantiates a new request authentication filter.
     *
     * @throws NamingException the naming exception
     */
    public RequestAuthenticationFilter() throws NamingException {
        authenticationService = BeanHelper.lookup(AuthenticationService.class);
        userContext = BeanHelper.lookup(UserContext.class);
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * javax.ws.rs.container.ContainerRequestFilter#filter(javax.ws.rs.container
	 * .ContainerRequestContext)
     */
    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        if (isRequestWithAuthentication(requestContext)) {
            Cookie authCookie = requestContext.getCookies().get("authorization");
            String authToken = authCookie.getValue();
            try {
                Person authenticatedPerson = authenticationService.authenticate(authToken);
                userContext.setPerson(authenticatedPerson);
            } catch (HawAuthenticationException e) {
                //Proceed as unauthenticated user
            } catch (HawException e) {
                throw new WebApplicationException(e, e.getErrorStatus());
            }
        }
        requestContext.setSecurityContext(createUserSecurityContext(requestContext));
    }

    /**
     * Checks if is request with authentication.
     *
     * @param requestContext the request context
     * @return true, if is request with authentication
     */
    private boolean isRequestWithAuthentication(ContainerRequestContext requestContext) {
        return requestContext.getCookies().containsKey("authorization")
                && requestContext.getHeaderString("authorization") == null;
    }

    /**
     * Creates the user security context.
     *
     * @param requestContext the request context
     * @return the security context
     */
    private SecurityContext createUserSecurityContext(final ContainerRequestContext requestContext) {
        return new UserSecurityContext(userContext, requestContext
                .getSecurityContext().isSecure(), requestContext
                        .getSecurityContext().getAuthenticationScheme());
    }

}
