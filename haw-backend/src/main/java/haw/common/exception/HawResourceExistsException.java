package haw.common.exception;

import javax.ws.rs.core.Response.Status;

/**
 * Business exception which indicates that a resource does already exist.
 * 
 * @author Uwe Eisele
 */
public class HawResourceExistsException extends HawBusinessException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new jak resource exists exception.
	 */
	public HawResourceExistsException() {
		super();
	}

	/**
	 * Instantiates a new jak resource exists exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public HawResourceExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new jak resource exists exception.
	 * 
	 * @param message
	 *            the message
	 */
	public HawResourceExistsException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new jak resource exists exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public HawResourceExistsException(Throwable cause) {
		super(cause);
	}

	/**
	 * Returns the corresponding HTTP Status: 409 Conflict.
	 * 
	 * @return the error status
	 */
	@Override
	public Status getErrorStatus() {
		return Status.CONFLICT;
	}
}
