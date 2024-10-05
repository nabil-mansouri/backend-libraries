package com.nm.products.converter;

import com.nm.products.dtos.forms.CategoryFormDto;
import com.nm.products.dtos.forms.IngredientFormDto;
import com.nm.products.dtos.forms.ProductFormDto;
import com.nm.products.dtos.forms.ProductPartFormDto;
import com.nm.products.model.Category;
import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.ProductDefinition;
import com.nm.products.model.ProductDefinitionPart;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 *
 */
public interface ProductDefinitionFormConverter {

	public CategoryFormDto toDto(Category category, OptionsList options);

	public IngredientFormDto toDto(IngredientDefinition ingredient, OptionsList options);

	public ProductFormDto toDto(ProductFormDto form, OptionsList options);

	public ProductFormDto toDto(ProductDefinition product, OptionsList options);

	public ProductPartFormDto toDto(ProductDefinitionPart part, OptionsList options);

	public Category toEntity(CategoryFormDto category, OptionsList options) throws NoDataFoundException;

	public IngredientDefinition toEntity(IngredientFormDto ingredient, OptionsList options)
			throws NoDataFoundException;

	public ProductDefinition toEntity(ProductFormDto product, OptionsList options) throws NoDataFoundException;

	public ProductDefinitionPart toEntity(ProductPartFormDto part, OptionsList options);

}