package haw.common.exception;

/**
 * Root for all business exceptions of the JAK application.
 * 
 * @author Uwe Eisele
 */
public abstract class HawBusinessException extends HawException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new jak business exception.
	 */
	public HawBusinessException() {
		super();
	}

	/**
	 * Instantiates a new jak business exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public HawBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new jak business exception.
	 * 
	 * @param message
	 *            the message
	 */
	public HawBusinessException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new jak business exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public HawBusinessException(Throwable cause) {
		super(cause);
	}

}
