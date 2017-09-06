/**
 *
 */
package haw.common.error.handler;

import haw.common.exception.*;
import haw.common.helper.StringHelper;
import haw.common.logging.LoggerProducer;
import haw.common.resource.type.Role;

import java.security.Principal;
import java.util.Map;
import javax.enterprise.context.Dependent;

import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.slf4j.Logger;

/**
 * This class contains the exception handling for modules.
 *
 * @author Katharina Knaus
 * @author Uwe Eisele
 */
@Dependent
public class HawErrorHandler {

    /**
     * The class of the injector
     */
    private final Class<?> CLASS;

    /**
     * The logger
     */
    private final Logger LOGGER;

    /**
     * Instantiates a new error handler based on a InjectionPoint
     *
     * @param ip the injection point
     */
    @Inject
    public HawErrorHandler(InjectionPoint ip) {
        this.CLASS = ip.getMember().getDeclaringClass();
        this.LOGGER = LoggerProducer.getLogger(CLASS);
    }

    /**
     * Instantiates a new error handler.
     *
     * @param clazz The class for the logger
     */
    public HawErrorHandler(Class<?> clazz) {
        this.CLASS = clazz;
        this.LOGGER = LoggerProducer.getLogger(CLASS);
    }

    /**
     * Returns a new error handler.
     *
     * @param clazz The class for the logger
     * @return the new error handler
     */
    public static HawErrorHandler getInstance(Class<?> clazz) {
        return new HawErrorHandler(clazz);
    }

    /**
     * Handles an authentication error
     *
     * @throws HawAuthenticationException The exception which is thrown
     */
    public void handleAuthenticationError() throws HawAuthenticationException {
        throw new HawAuthenticationException("Invalid Username or Password.");
    }

    /**
     * Handle authorization error.
     *
     * @param principal the principal who caused the exception
     * @param nameOfResource the name of the resource
     * @param expectedRole the expected role
     * @param attribute the changed attribute
     * @throws HawAuthorizationException The exception which is thrown
     */
    public void handleAuthorizationErrorUpdateAttribute(Principal principal,
            String nameOfResource, Role expectedRole, String attribute)
            throws HawAuthorizationException {
        handleAuthorizationErrorUpdateAttribute(principal, nameOfResource,
                expectedRole.name(), attribute);
    }

    /**
     * Handle authorization error.
     *
     * @param principal the principal who caused the exception
     * @param nameOfResource the name of the resource
     * @param expectedRole the expected role
     * @param attribute the changed attribute
     * @throws HawAuthorizationException The exception which is thrown
     */
    public void handleAuthorizationErrorUpdateAttribute(Principal principal,
            String nameOfResource, String expectedRole, String attribute)
            throws HawAuthorizationException {
        String action = String.format("Update of attribute '%s'.", attribute);
        handleAuthorizationError(principal, nameOfResource, expectedRole,
                action);
    }

    /**
     * Handle authorization error.
     *
     * @param principal the principal who caused the exception
     * @param nameOfResource the name of the resource
     * @param expectedRole the expected role
     * @param action the action
     * @throws HawAuthorizationException The exception which is thrown
     */
    public void handleAuthorizationError(Principal principal,
            String nameOfResource, String expectedRole, String action)
            throws HawAuthorizationException {
        String message = String.format(
                "User must be at least in role '%s'. Action: %s", expectedRole,
                action);
        handleAuthorizationError(principal, nameOfResource, message);
    }

    /**
     * Handle authorization error.
     *
     * @param principal the principal who caused the exception
     * @param nameOfResource the name of the resource
     * @param message the error message
     * @throws HawAuthorizationException The exception which is thrown
     */
    public void handleAuthorizationError(Principal principal,
            String nameOfResource, String message)
            throws HawAuthorizationException {
        String username = (principal != null ? principal.getName()
                : "anonymouse");
        String formattedMessage = String
                .format("Forbidden - Not allowed for user '%s' to access resource '%s'. %s",
                        username, nameOfResource, message);
        LOGGER.warn(formattedMessage);
        throw new HawAuthorizationException(formattedMessage);
    }

    /**
     * Handles a resource not found error.
     *
     * @param e The cause
     * @param nameOfResource The name of the resource
     * @param queryName The name of the query
     * @param parameters The parameters of the query
     * @throws HawResourceNotExistsException The exception which is thrown
     */
    public void handleResourceNotFoundError(Throwable e, String nameOfResource,
            String queryName, Map<?, ?> parameters)
            throws HawResourceNotExistsException {
        String message = String
                .format("Can't find resource '%s' with query '%s' and parameters '%s'.",
                        nameOfResource, queryName,
                        StringHelper.mapToString(parameters));
        LOGGER.warn(message, e);
        throw new HawResourceNotExistsException(message, e);
    }

    /**
     * Handles a resource not found error.
     *
     * @param e The cause
     * @param nameOfResource The name of the resource
     * @param queryName The name of the query
     * @throws HawResourceNotExistsException The exception which is thrown
     */
    public void handleResourceNotFoundError(Throwable e, String nameOfResource,
            String queryName) throws HawResourceNotExistsException {
        String message = String.format(
                "Can't find resource '%s' with query '%s'.", nameOfResource,
                queryName);
        LOGGER.warn(message, e);
        throw new HawResourceNotExistsException(message, e);
    }

    /**
     * Handles a resource not found error.
     *
     * @param nameOfResource The name of the resource
     * @param id The id of the resource
     * @throws HawResourceNotExistsException The exception which is thrown
     */
    public void handleResourceNotFoundError(String nameOfResource, int id)
            throws HawResourceNotExistsException {
        String message = String.format(
                "Can't find resource '%s' with id '%d'.", nameOfResource, id);
        LOGGER.warn(message);
        throw new HawResourceNotExistsException(message);
    }

    /**
     * Handles a resource conflict error.
     *
     * @param e the causing error
     * @param nameOfResource The name of the resource
     * @param id The id of the resource
     * @throws HawResourceConflictException The exception which is thrown
     */
    public void handleResourceAlreadyUpdatedError(Throwable e, String nameOfResource,
            int id) throws HawResourceConflictException {
        String message = String
                .format("Resource '%s' with id '%d' has already been updated by someone else.",
                        nameOfResource, id);
        LOGGER.warn(message);
        throw new HawResourceConflictException(message, e);
    }

    /**
     * Handles a resource conflict error.
     *
     * @param e the causing error
     * @param nameOfResource The name of the resource
     * @param id The id of the resource
     * @throws HawResourceConflictException The exception which is thrown
     */
    public void handleResourceConstraintViolationError(Throwable e, String nameOfResource,
            int id) throws HawResourceConflictException {
        String message = String
                .format("Constraint violation on resource '%s' with id '%d': %s",
                        nameOfResource, id, e.getMessage());
        LOGGER.warn(message);
        throw new HawResourceConflictException(message, e);
    }

    /**
     * Handles invalid parameter error.
     *
     * @param parameterName The name of the parameter with violates the
     * validation constraint
     * @param expected The expected value
     * @param actual The actual value
     * @throws HawValidationException Thrown if parameters for validation are
     * wrong
     */
    public void handleValidationErrorInvalidParameter(String parameterName, String expected, String actual)
            throws HawValidationException {
        String message = String.format("Validation Error: Parameter '%s' is invalid. Expected '%s', but was '%s'.",
                parameterName, expected, actual);
        LOGGER.warn(message);
        throw new HawValidationException(message);
    }

    /**
     * Handles a technical resource error.
     *
     * @param e The cause
     * @param nameOfResource The name of the resource
     * @throws HawServerException The exception which is thrown
     */
    public void handleTechnicalResourceError(Throwable e, String nameOfResource)
            throws HawServerException {
        String message = String.format("Can't access resource '%s' : %s - %s",
                nameOfResource, e.getClass().getName(), e.getMessage());
        LOGGER.error(message, e);
        throw new HawServerException(message, e);
    }

    /**
     * Handles a resource not unique error.
     *
     * @param e The cause
     * @param nameOfResource The name of the resource
     * @param queryName The name of the query
     * @param parameters The parameters of the query
     * @throws HawServerException The exception which is thrown
     */
    public void handleTechnicalResourceNotUniqueError(Throwable e,
            String nameOfResource, String queryName, Map<?, ?> parameters)
            throws HawServerException {
        String message = String
                .format("Non unique result for resource '%s' with query '%s' and parameters '%s'.",
                        nameOfResource, queryName,
                        StringHelper.mapToString(parameters));
        LOGGER.error(message, e);
        throw new HawServerException(message, e);
    }

    /**
     * Handles a technical file error.
     *
     * @param e The cause
     * @param nameOfResource The name of the resource
     * @param action The action
     * @throws HawServerException The exception which is thrown
     */
    public void handleTechnicalFileError(Throwable e, String nameOfResource, String action)
            throws HawServerException {
        String message = String.format("Can't %s file '%s' : %s - %s",
                action, nameOfResource, e.getClass().getName(), e.getMessage());
        LOGGER.error(message, e);
        throw new HawServerException(message, e);
    }

    /**
     * Handles a initialization error
     *
     * @param e The cause
     * @param object The object which could not be initialized
     * @throws HawServerException The exception which is thrown
     */
    public void handleTechnicalInitializationError(Throwable e, String object)
            throws HawServerException {
        String message = String.format("Error during initialization of '%s'. Cause: %s - %s",
                object, e.getClass(), e.getMessage());
        LOGGER.error(message, e);
        throw new HawServerException(message, e);
    }
}
