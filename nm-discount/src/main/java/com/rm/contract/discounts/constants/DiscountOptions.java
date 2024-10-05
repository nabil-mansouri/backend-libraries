package com.rm.contract.discounts.constants;

import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.dtos.ModelOptionsType;


/**
 * 
 * @author Nabil
 * 
 */
public enum DiscountOptions implements ModelOptions {
	Rules,Trigger,Communication;

	public ModelOptionsType getType() {
		return ModelOptionsType.Discount;
	}

}