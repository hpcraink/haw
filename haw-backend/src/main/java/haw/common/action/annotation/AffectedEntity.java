package haw.common.action.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * Qualifier for the entity which was directly affected by a action.
 *
 * @author Uwe Eisele
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
public @interface AffectedEntity {

    /**
     * The name of the affected entity.
     *
     * @return the string
     */
    public String value();

}
