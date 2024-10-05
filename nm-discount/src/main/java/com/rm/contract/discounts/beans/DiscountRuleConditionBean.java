package com.rm.contract.discounts.beans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscountRuleConditionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	private boolean productCondition;
	private Long product;
	private Long qty;

	public boolean isProductCondition() {
		return productCondition;
	}

	public DiscountRuleConditionBean setProductCondition(boolean productCondition) {
		this.productCondition = productCondition;
		return this;
	}

	public Long getProduct() {
		return product;
	}

	public DiscountRuleConditionBean setProduct(Long product) {
		this.product = product;
		return this;
	}

	public Long getQty() {
		return qty;
	}

	public DiscountRuleConditionBean setQty(Long qty) {
		this.qty = qty;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
