package com.nm.plannings.soa;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class PlanningException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlanningException() {
	}

	public PlanningException(String message) {
		super(message);
	}

	public PlanningException(Throwable cause) {
		super(cause);
	}

	public PlanningException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlanningException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
