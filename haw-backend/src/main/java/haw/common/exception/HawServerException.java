package haw.common.exception;

import javax.ws.rs.core.Response.Status;

/**
 * Technical exceptions of the JAK application.
 * 
 * @author Uwe Eisele
 */
public class HawServerException extends HawException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new jak server exception.
	 */
	public HawServerException() {
		super();
	}

	/**
	 * Instantiates a new jak server exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public HawServerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new jak server exception.
	 * 
	 * @param message
	 *            the message
	 */
	public HawServerException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new jak server exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public HawServerException(Throwable cause) {
		super(cause);
	}

	/**
	 * Returns the corresponding HTTP Status: 500 Internal Server Error.
	 * 
	 * @return the error status
	 */
	@Override
	public Status getErrorStatus() {
		return Status.INTERNAL_SERVER_ERROR;
	}
}
