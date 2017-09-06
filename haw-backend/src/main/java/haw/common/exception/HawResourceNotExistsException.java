package haw.common.exception;

import javax.ws.rs.core.Response.Status;

/**
 * Business exception which indicates that a resource does not exist.
 * 
 * @author Uwe Eisele
 */
public class HawResourceNotExistsException extends HawBusinessException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new jak resource not exists exception.
	 */
	public HawResourceNotExistsException() {
		super();
	}


	/**
	 * Instantiates a new jak resource not exists exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public HawResourceNotExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new jak resource not exists exception.
	 * 
	 * @param message
	 *            the message
	 */
	public HawResourceNotExistsException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new jak resource not exists exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public HawResourceNotExistsException(Throwable cause) {
		super(cause);
	}

	/**
	 * Returns the corresponding HTTP Status: 404 Not Found.
	 * 
	 * @return the error status
	 */
	@Override
	public Status getErrorStatus() {
		return Status.NOT_FOUND;
	}
}
