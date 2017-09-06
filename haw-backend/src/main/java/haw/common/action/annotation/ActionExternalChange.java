package haw.common.action.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * This Qualifier is responsible for Events which has been fired because one
 * entity has been changed.
 *
 * @see Event
 * @author Uwe Eisele
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
public @interface ActionExternalChange {
    // do nothing
}
