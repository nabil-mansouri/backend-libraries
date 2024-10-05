package com.nm.bridges.prices.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.bridges.prices.OrderType;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderTypeFormDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderType orderType;
	private boolean selected;

	public OrderTypeFormDto() {
	}

	public OrderTypeFormDto(OrderType o) {
		setOrderType(o);
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public boolean isSelected() {
		return selected;
	}

	public OrderTypeFormDto setSelected(boolean selected) {
		this.selected = selected;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
