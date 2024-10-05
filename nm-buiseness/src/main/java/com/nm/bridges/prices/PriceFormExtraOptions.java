package com.nm.bridges.prices;

import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.dtos.ModelOptionsType;

/**
 * 
 * @author Nabil
 * 
 */
public enum PriceFormExtraOptions implements ModelOptions {
	Product, Products;

	public ModelOptionsType getType() {
		return PriceFormOptionsType.Price;
	}

	public enum PriceFormOptionsType implements ModelOptionsType {
		Price;
	}
}