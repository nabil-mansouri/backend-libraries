package com.nm.products.converter;

import com.nm.products.dtos.forms.CategoryTreeDto;
import com.nm.products.model.Category;

/**
 * 
 * @author nabilmansouri
 *
 */
public interface CategoryTreeListener {
	public void onCategory(Category category, CategoryTreeDto tree);
}
