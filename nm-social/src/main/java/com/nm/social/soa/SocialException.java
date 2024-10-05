package com.nm.social.soa;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class SocialException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SocialException() {
		super();

	}

	public SocialException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public SocialException(String message, Throwable cause) {
		super(message, cause);

	}

	public SocialException(String message) {
		super(message);

	}

	public SocialException(Throwable cause) {
		super(cause);

	}

}
