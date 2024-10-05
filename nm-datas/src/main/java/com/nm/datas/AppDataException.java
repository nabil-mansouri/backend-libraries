package com.nm.datas;

/**
 * 
 * @author nabilmansouri
 *
 */
public class AppDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppDataException() {

	}

	public AppDataException(String message) {
		super(message);

	}

	public AppDataException(Throwable cause) {
		super(cause);

	}

	public AppDataException(String message, Throwable cause) {
		super(message, cause);

	}

	public AppDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
