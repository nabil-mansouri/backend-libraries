package com.nm.utils.http;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class HttpResponseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public HttpResponseException() {

	}

	public HttpResponseException(String arg0) {
		super(arg0);

	}

	public HttpResponseException(Throwable arg0) {
		super(arg0);

	}

	public HttpResponseException(String arg0, Throwable arg1) {
		super(arg0, arg1);

	}

}
