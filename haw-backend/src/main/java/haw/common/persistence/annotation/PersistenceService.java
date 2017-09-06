package haw.common.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * Qualifier annotation for the HawPersistenceService. This Annotation is used
 * to configure the service.
 *
 * That is at @Injection point, annotated classes/types/methods with (default)
 * fireActions = true on Create-, Update- and Delete- events will be fired. This
 * is only false for the loggingService (otherwise recursion would happen,
 * badness would happen, little kitties would die...)
 *
 * @author Uwe Eisele
 *
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
// Previous (Netbeans complains:
//   @Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER, ElementType.CONSTRUCTOR })
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
public @interface PersistenceService {

    /**
     * Indicates if entity actions should be fired.
     *
     * @return return true to indicate this action should be fired;
     */
    @Nonbinding
    boolean fireActions() default true;
}
