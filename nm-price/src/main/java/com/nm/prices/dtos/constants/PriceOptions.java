package com.nm.prices.dtos.constants;

import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.dtos.ModelOptionsType;

/**
 * 
 * @author Nabil
 * 
 */
public enum PriceOptions implements ModelOptions {
	ComputeTotal, SetPrices, Additionnal;
	public ModelOptionsType getType() {
		return PriceOptionsType.Price;
	}

	public enum PriceOptionsType implements ModelOptionsType {
		Price
	}
}