package com.nm.products.converter;

import com.nm.products.dtos.navigation.NavigationBean;
import com.nm.products.dtos.views.ProductAsListDto;
import com.nm.products.dtos.views.ProductAsTreeDto;
import com.nm.products.model.ProductDefinition;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil
 *
 */
public interface ProductDefinitionGraphConverter {

	public ProductAsListDto toDto(ProductDefinition product, OptionsList options);

	public ProductAsTreeDto toDto(NavigationBean navigation, OptionsList options);
}