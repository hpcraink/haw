package haw.common.action.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.event.Event;
import javax.inject.Qualifier;

/**
 * This Qualifier is responsible for {@link Event} which has been fired because
 * one entity has been deleted.
 *
 * @see javax.enterprise.event.Event
 * @author Katharina
 * @author Martin
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
public @interface ActionDelete {
    // do nothing
}
