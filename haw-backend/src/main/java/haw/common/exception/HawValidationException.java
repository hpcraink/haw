package haw.common.exception;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Response.Status;

/**
 * Business exception which indicates that a resource is invalid.
 * 
 * @author Uwe Eisele
 */
public class HawValidationException extends HawBusinessException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The violations. */
	private final Set<String> violations;

	/**
	 * Instantiates a new violation exception.
	 * @param violations Set of occurred violations.
	 */
	public HawValidationException(Collection<String> violations) {
		super(violationsToMessage(violations));
		this.violations = Collections.unmodifiableSet(new HashSet<>(violations));
	}

	/**
	 * Instantiates a new violation exception.
	 * 
	 * @param violation
	 *            Occurred violation
	 * @param cause
	 *            the cause
	 */
	public HawValidationException(String violation, Throwable cause) {
		super(violation, cause);
		Set<String> violationsLokal = new HashSet<>();
		violationsLokal.add(violation);
		this.violations = Collections.unmodifiableSet(violationsLokal);
	}

	/**
	 * Instantiates a new violation exception.
	 * @param violation Occurred violation
	 */
	public HawValidationException(String violation) {
		super(violation);
		Set<String> violationsLokal = new HashSet<>();
		violationsLokal.add(violation);
		this.violations = Collections.unmodifiableSet(violationsLokal);
	}

	/**
	 * Returns the set of occurred violations.
	 * 
	 * @return the violations
	 */
	public Set<String> getViolations() {
		return violations;
	}

	/**
	 * Returns the corresponding HTTP Status: 400 Bad Request.
	 * 
	 * @return the error status
	 */
	@Override
	public Status getErrorStatus() {
		return Status.BAD_REQUEST;
	}

	/**
	 * Adds all given violations to one String, separating violations by
	 * newLine.
	 * 
	 * @param violations
	 *            the violations to String
	 * @return the string
	 */
	private static String violationsToMessage(Collection<String> violations) {
		StringBuilder buffer = new StringBuilder("");
		for(String violation : violations) {
			buffer.append(violation);
			buffer.append("\n");
		}
		return buffer.toString();
	}
}
