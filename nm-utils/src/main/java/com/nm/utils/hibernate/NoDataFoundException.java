package com.nm.utils.hibernate;

/**
 * 
 * @author Nabil
 *
 */
public class NoDataFoundException extends Exception {

	private static final long serialVersionUID = -1060173861031364969L;

	public NoDataFoundException(Throwable cause) {
		super(cause);
	}

	public NoDataFoundException(String message) {
		this(message, null);
	}

	public NoDataFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
