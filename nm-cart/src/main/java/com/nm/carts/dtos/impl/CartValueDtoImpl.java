package com.nm.carts.dtos.impl;

import com.nm.app.currency.CurrencyDto;
import com.nm.utils.dtos.Dto;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CartValueDtoImpl implements Dto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CurrencyDto currency;
	private Double value;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public CurrencyDto getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyDto currency) {
		this.currency = currency;
	}
}
