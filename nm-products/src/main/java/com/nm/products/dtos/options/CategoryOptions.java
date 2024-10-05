package com.nm.products.dtos.options;

import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.dtos.ModelOptionsType;

/**
 * 
 * @author Nabil
 * 
 */
public enum CategoryOptions implements ModelOptions {
	ProductCount;
	public ModelOptionsType getType() {
		return ProductOptionsType.Category;
	}
}