package com.nm.dictionnary.models;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class DictionnaryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DictionnaryException() {
		super();

	}

	public DictionnaryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public DictionnaryException(String message, Throwable cause) {
		super(message, cause);

	}

	public DictionnaryException(String message) {
		super(message);

	}

	public DictionnaryException(Throwable cause) {
		super(cause);

	}

}
