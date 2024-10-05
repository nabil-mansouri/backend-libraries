package com.nm.utils.dtos;

/**
 * 
 * @author Nabil
 *
 */
public class DtoConvertException extends Exception {

	private static final long serialVersionUID = -1060173861031364969L;

	public DtoConvertException(Throwable cause) {
		super(cause);
	}

	public DtoConvertException(String message) {
		this(message, null);
	}

	public DtoConvertException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
