package haw.common.exception;

import javax.ws.rs.core.Response.Status;

/**
 * Business exception which indicates that a user is not authorizated.
 * 
 * @author Katharina
 */
public class HawAuthorizationException extends HawBusinessException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new jak authorization exception.
	 */
	public HawAuthorizationException() {
		super();
	}

	/**
	 * Instantiates a new jak authorization exception.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public HawAuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new jak authorization exception.
	 * 
	 * @param message the message
	 */
	public HawAuthorizationException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new jak authorization exception.
	 * 
	 * @param cause the cause
	 */
	public HawAuthorizationException(Throwable cause) {
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
