package com.nm.app.async;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class AsyncResultDto<T> {
	public static enum Status {
		NotReady, Ready, Failed
	}

	private T data;
	private Status status;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
