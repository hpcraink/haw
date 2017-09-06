package haw.common.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * A class or method which is annotated with this Annotation gets automatically
 * tracked. This means that the call and the return of the method(s) are logged.
 *
 * @author Uwe Eisele
 */
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Track {
    // do nothing
}
