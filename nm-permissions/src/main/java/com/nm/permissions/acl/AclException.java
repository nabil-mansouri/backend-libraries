package com.nm.permissions.acl;

/**
 * 
 * @author Nabil
 *
 */
public class AclException extends Exception {

	private static final long serialVersionUID = -1060173861031364969L;

	public AclException() {
	}

	public AclException(String message) {
		this(message, null);
	}

	public AclException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public AclException(Exception e) {
		super(e);
	}
}
