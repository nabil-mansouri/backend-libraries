package com.nm.cms;

/**
 * 
 * @author Nabil
 *
 */
public class CmsException extends Exception {

	private static final long serialVersionUID = -1060173861031364969L;

	public CmsException() {
	}

	public CmsException(String message) {
		this(message, null);
	}

	public CmsException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public CmsException(Exception e) {
		super(e);
	}
}
