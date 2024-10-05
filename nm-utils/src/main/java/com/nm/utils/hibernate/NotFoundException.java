package com.nm.utils.hibernate;

/**
 * 
 * @author Nabil
 *
 */
public class NotFoundException extends Exception {

	private static final long serialVersionUID = -1060173861031364969L;

	public NotFoundException(Throwable cause) {
		super(cause);
	}

	public NotFoundException(String message) {
		this(message, null);
	}

	public NotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
