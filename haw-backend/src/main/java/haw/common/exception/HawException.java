package haw.common.exception;

import javax.ws.rs.core.Response.Status;

/**
 * Root exception for JAK application.
 * 
 * @author Uwe Eisele
 */
public abstract class HawException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new jak exception.
	 */
	public HawException() {
		super();
	}

	/**
	 * Instantiates a new jak exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public HawException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new jak exception.
	 * 
	 * @param message
	 *            the message
	 */
	public HawException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new jak exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public HawException(Throwable cause) {
		super(cause);
	}

	/**
	 * Returns the corresponding HTTP status code of this exception (must 4xx or
	 * 5xx).
	 * 
	 * @return the error status
	 */
	public abstract Status getErrorStatus();

}
