package haw.common.action.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * Qualifier for identifying the attribute which is affected by a action.
 *
 * @author Katharina
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface AffectedAttribute {

    /**
     * The name of the affected attribute.
     *
     * @return the name
     */
    public String value();

}
