package com.nm.geo;

/**
 * 
 * @author Nabil
 *
 */
public class GeoException extends Exception {

	private static final long serialVersionUID = -1060173861031364969L;

	public GeoException() {
	}

	public GeoException(String message) {
		this(message, null);
	}

	public GeoException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public GeoException(Exception e) {
		super(e);
	}
}
