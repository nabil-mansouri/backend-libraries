package com.nm.prices.dtos.constants;

import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.dtos.ModelOptionsType;

/**
 * 
 * @author Nabil
 * 
 */
public enum PriceFormOptions implements ModelOptions {
	Subject;

	public ModelOptionsType getType() {
		return PriceFormOptionsType.Price;
	}

	public enum PriceFormOptionsType implements ModelOptionsType {
		Price;
	}

}