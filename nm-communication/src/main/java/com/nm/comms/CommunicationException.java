package com.nm.comms;

/**
 * 
 * @author Nabil
 *
 */
public class CommunicationException extends Exception {

	private static final long serialVersionUID = -1060173861031364969L;

	public CommunicationException() {
	}

	public CommunicationException(String message) {
		this(message, null);
	}

	public CommunicationException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public CommunicationException(Exception e) {
		super(e);
	}
}
