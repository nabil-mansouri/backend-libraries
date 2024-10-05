package com.nm.shop.constants;

import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.dtos.ModelOptionsType;

/**
 * 
 * @author Nabil
 * 
 */
public enum ShopOptions implements ModelOptions {
	States;
	public ModelOptionsType getType() {
		return ShopOptionsType.Shop;
	}

	public enum ShopOptionsType implements ModelOptionsType {
		Shop
	}
}