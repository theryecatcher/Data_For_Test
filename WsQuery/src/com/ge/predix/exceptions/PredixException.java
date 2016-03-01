/**
 * 
 */
package com.ge.predix.exceptions;

/**
 * @author Ravi_Shankar10
 *
 */
public class PredixException extends Exception {

	/**
	 * Default generated long serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public PredixException() {
		super("Error happened while trying to connect to Predix. Please check application configuration and try again.",
				new Exception());
	}

	/**
	 * @param message
	 */
	public PredixException(String message) {
		super(message, new Exception());
	}

	/**
	 * @param cause
	 */
	public PredixException(Throwable cause) {
		super(cause.getMessage(), cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PredixException(String message, Throwable cause) {
		super(message, cause);
	}

}
