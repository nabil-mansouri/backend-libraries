package com.rm.soa.discounts.lifecycle.beans;

import com.rm.model.discounts.DiscountDefinition;

/**
 * 
 * @author Nabil
 * 
 */
public class DiscountLifeCyclePlanBean {
	private Long client;
	private DiscountDefinition discount;

	public Long getClient() {
		return client;
	}

	public void setClient(Long client) {
		this.client = client;
	}

	public DiscountDefinition getDiscount() {
		return discount;
	}

	public void setDiscount(DiscountDefinition discount) {
		this.discount = discount;
	}

}
