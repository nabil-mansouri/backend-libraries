package com.rm.contract.commons.exceptions;
/**
 * 
 * @author Nabil
 *
 */
public class NoLocaleException extends Exception {
	
	private static final long	serialVersionUID	= -1060173861031364969L;
	public NoLocaleException() {
	}
	public NoLocaleException(String message) {
		this(message, null);
	}
	
	public NoLocaleException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
