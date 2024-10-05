package com.nm.carts.exceptions;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CartManagementException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CartManagementException() {

	}

	public CartManagementException(String message) {
		super(message);

	}

	public CartManagementException(Throwable cause) {
		super(cause);

	}

	public CartManagementException(String message, Throwable cause) {
		super(message, cause);

	}

	public CartManagementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
