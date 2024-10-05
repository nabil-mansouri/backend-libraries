package com.nm.plannings;

/**
 * 
 * @author Nabil
 * 
 */
public class InvalidPlanningDaysException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPlanningDaysException() {
		super();

	}

	public InvalidPlanningDaysException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public InvalidPlanningDaysException(String message, Throwable cause) {
		super(message, cause);

	}

	public InvalidPlanningDaysException(String message) {
		super(message);

	}

	public InvalidPlanningDaysException(Throwable cause) {
		super(cause);

	}

}
