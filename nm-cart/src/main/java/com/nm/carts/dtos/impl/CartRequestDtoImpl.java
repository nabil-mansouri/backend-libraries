package com.nm.carts.dtos.impl;

import com.nm.carts.constants.CartOperation;
import com.nm.carts.contract.CartEvent;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class CartRequestDtoImpl {
	private CartOperation operation;
	private CartEvent event;
	private Long number;
	private String uuid;

	public CartEvent getEvent() {
		return event;
	}

	public CartRequestDtoImpl setEvent(CartEvent event) {
		this.event = event;
		return this;
	}

	public CartOperation getOperation() {
		return operation;
	}

	public CartRequestDtoImpl setOperation(CartOperation operation) {
		this.operation = operation;
		return this;
	}

	public Long getNumber() {
		return number;
	}

	public CartRequestDtoImpl setNumber(Long number) {
		this.number = number;
		return this;
	}

	public String getUuid() {
		return uuid;
	}

	public CartRequestDtoImpl setUuid(String uuid) {
		this.uuid = uuid;
		return this;
	}

}
