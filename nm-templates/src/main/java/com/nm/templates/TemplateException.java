package com.nm.templates;


/**
 * 
 * @author Nabil
 *
 */
public class TemplateException extends Exception {

	private static final long serialVersionUID = -1060173861031364969L;

	public TemplateException() {
	}

	public TemplateException(String message) {
		this(message, null);
	}

	public TemplateException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public TemplateException(Exception e) {
		super(e);
	}
}
