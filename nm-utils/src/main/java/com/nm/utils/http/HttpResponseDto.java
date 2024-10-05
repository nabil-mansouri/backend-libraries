package com.nm.utils.http;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * 
 * @author Nabil Mansouri Penser à modifier MessageHeaders
 */
@JsonAutoDetect
public class HttpResponseDto implements Serializable {

	@Override
	public String toString() {
		return "HttpResponseBean [status=" + status + ", body=" + body + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	private String type;
	private int status;
	private String body;
	private byte[] bodyBytes;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getBodyBytes() {
		return bodyBytes;
	}

	public void setBodyBytes(byte[] bodyBytes) {
		this.bodyBytes = bodyBytes;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
