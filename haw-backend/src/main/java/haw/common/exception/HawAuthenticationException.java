package haw.common.exception;

import javax.ws.rs.core.Response.Status;

/**
 * Business exception which indicates that a user is not authenticated.
 * 
 * @author Uwe Eisele
 */
public class HawAuthenticationException extends HawBusinessException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new jak authentication exception.
	 */
	public HawAuthenticationException() {
		super();
	}

	/**
	 * Instantiates a new jak authentication exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public HawAuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new jak authentication exception.
	 * 
	 * @param message
	 *            the message
	 */
	public HawAuthenticationException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new jak authentication exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public HawAuthenticationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Returns the corresponding HTTP Status: 403 Forbidden.
	 * 
	 * @return the error status
	 */
	@Override
	public Status getErrorStatus() {
		return Status.FORBIDDEN;
	}
}
