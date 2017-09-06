package haw.common.validator;

import haw.common.exception.HawValidationException;
import haw.common.logging.LoggerProducer;

import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.Dependent;

import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;

/**
 * This class is responsible for validation of entity constraints.
 *
 * @see Validator
 * @author Katharina
 */
@Dependent
public class HawValidator {

    /**
     * The validator.
     */
    @Inject
    private Validator validator;

    /**
     * The logger
     */
    private final Logger LOGGER;

    /**
     * Instantiates a new validator based on a InjectionPoint
     *
     * @param ip the injection point
     */
    @Inject
    public HawValidator(InjectionPoint ip) {
        Class<?> clazz = ip.getMember().getDeclaringClass();
        this.LOGGER = LoggerProducer.getLogger(clazz);
    }

    /**
     * This method validates the bean constraints and throws a new exception.
     *
     * @param <T> The generic type
     * @param entity Entity to validate
     * @throws HawValidationException Is thrown if the constraints we defined
     * are violated. {@link HawValidationException} if the bean was not properly
     * filled.
     */
    public <T> void validate(T entity)
            throws HawValidationException {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            Set<String> violationMessages = new HashSet<>();
            for (ConstraintViolation<T> violation : violations) {
                violationMessages.add("In entity '" + entity.getClass().getSimpleName() + "': "
                        + "attribute: '" + violation.getPropertyPath() + "' "
                        + "violation: '" + violation.getMessage() + "'");
            }
            HawValidationException violationException = new HawValidationException(
                    violationMessages);
            LOGGER.error(violationException.getMessage(), violationException);
            throw violationException;
        }
    }

}
