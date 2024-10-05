package com.nm.products.dtos.options;

import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.dtos.ModelOptionsType;

/**
 * 
 * @author Nabil
 * 
 */
public enum ProductOptions implements ModelOptions {
	Parts, Ingredients, Categories, ProductsPart;
	public ModelOptionsType getType() {
		return ProductOptionsType.Product;
	}
}