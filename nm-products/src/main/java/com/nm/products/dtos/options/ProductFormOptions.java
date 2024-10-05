package com.nm.products.dtos.options;

import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.dtos.ModelOptionsType;

/**
 * 
 * @author Nabil
 * 
 */
public enum ProductFormOptions implements ModelOptions {
	Parts, Ingredients, Categories, Cms;
	public ModelOptionsType getType() {
		return ProductOptionsType.Product;
	}
}