package com.nm.utils.jdbc;
/**
 * 
 * @author Mansouri Nabil
 *
 */
public class JdbcDaoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JdbcDaoException() {
		super();
	
	}

	public JdbcDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	
	}

	public JdbcDaoException(String message, Throwable cause) {
		super(message, cause);
	
	}

	public JdbcDaoException(String message) {
		super(message);
	
	}

	public JdbcDaoException(Throwable cause) {
		super(cause);
	
	}

}
