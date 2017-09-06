package haw.common.exception;

import javax.ws.rs.core.Response.Status;

/**
 * Business exception which indicates that an update request conflicts with
 * another update request.
 * 
 * @author Uwe Eisele
 */
public class HawResourceConflictException extends HawBusinessException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new jak resource exists exception.
	 */
	public HawResourceConflictException() {
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
	public HawResourceConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new jak resource exists exception.
	 * 
	 * @param message
	 *            the message
	 */
	public HawResourceConflictException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new jak resource exists exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public HawResourceConflictException(Throwable cause) {
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
