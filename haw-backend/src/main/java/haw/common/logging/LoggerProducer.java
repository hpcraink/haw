package haw.common.logging;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a convenience class to enable injection of a Logger with @Inject
 * annotation.
 *
 * @author Uwe Eisele
 */
@ApplicationScoped
public class LoggerProducer {

    /**
     * Gets the logger.
     *
     * @param clazz the Class for which the logger should be created.
     * @return the logger for the given class.
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * Producer Method for a logger. The returned logger is instantiated for the
     * caller class.
     *
     * @param ip Injection point of injection. Required for getting the class of
     * the caller.
     * @return Logger for injecting class.
     */
    @Produces
    public Logger getLogger(InjectionPoint ip) {
        Class<?> caller = ip.getMember().getDeclaringClass();
        return LoggerFactory.getLogger(caller);
    }
}
