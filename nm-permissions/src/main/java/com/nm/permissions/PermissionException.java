package com.nm.permissions;

/**
 * 
 * @author Nabil
 *
 */
public class PermissionException extends Exception {

	private static final long serialVersionUID = -1060173861031364969L;

	public PermissionException() {
	}

	public PermissionException(String message) {
		this(message, null);
	}

	public PermissionException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public PermissionException(Exception e) {
		super(e);
	}
}
