package com.nm.products.dtos.navigation;

/**
 * 
 * @author nabilmansouri
 *
 */
public class NavigationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NavigationException() {

	}

	public NavigationException(String message) {
		super(message);

	}

	public NavigationException(Throwable cause) {
		super(cause);

	}

	public NavigationException(String message, Throwable cause) {
		super(message, cause);

	}

	public NavigationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
