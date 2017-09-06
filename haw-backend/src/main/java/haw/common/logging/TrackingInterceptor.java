package haw.common.logging;

import haw.common.security.UserContext;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interceptor for tracking. This interceptor is bound to the @Track annotation.
 *
 * @author Uwe Eisele
 */
@Track
@Interceptor
public class TrackingInterceptor {

    /**
     * The user context.
     */
    @Inject
    private UserContext userContext;

    /**
     * This method intercepts the call of the annotated method and logs the
     * entrance and the return of the corresponding method.
     *
     * @see InvocationContext#proceed()
     * @param ctx the {@link InvocationContext}
     * @return the return value of the next method in the chain
     * @throws Exception Thrown if something went wrong
     */
    @AroundInvoke
    public Object doLogging(InvocationContext ctx) throws Exception {
        Class<?> caller = ctx.getMethod().getDeclaringClass();
        Logger logger = LoggerFactory.getLogger(caller);
        logger.debug("Call of {}#{} for user {}",
                ctx.getMethod().getDeclaringClass().getName(),
                ctx.getMethod().getName(), userContext.getPrincipal().getName());
        Object result = ctx.proceed();
        logger.debug("Returned from {}#{} for user {}",
                ctx.getMethod().getDeclaringClass().getName(),
                ctx.getMethod().getName(), userContext.getPrincipal().getName());
        return result;
    }

}
