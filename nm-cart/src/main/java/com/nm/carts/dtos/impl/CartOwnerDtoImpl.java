package com.nm.carts.dtos.impl;

import com.nm.carts.dtos.CartOwnerDto;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CartOwnerDtoImpl implements CartOwnerDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sessionId;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
