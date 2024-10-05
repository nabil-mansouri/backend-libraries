package com.nm.products.converter;

import com.nm.products.dtos.forms.CategoryTreeDto;
import com.nm.products.dtos.forms.ProductFormDto;
import com.nm.products.dtos.views.CategoryViewDto;
import com.nm.products.dtos.views.IngredientViewDto;
import com.nm.products.dtos.views.ProductPartViewDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.model.Category;
import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.ProductDefinition;
import com.nm.products.model.ProductDefinitionIngredient;
import com.nm.products.model.ProductDefinitionPart;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil
 *
 */
public interface ProductDefinitionViewConverter {

	public CategoryViewDto toDto(Category category, OptionsList options);

	public CategoryTreeDto toTreeDto(Category category, OptionsList options, CategoryTreeListener... listener);

	public CategoryTreeDto toTreeDto(Category category, OptionsList options);

	public IngredientViewDto toDto(ProductDefinitionIngredient ingredient, OptionsList options);

	public IngredientViewDto toDto(IngredientDefinition ingredient, OptionsList options);

	public ProductViewDto toDto(ProductDefinition product, OptionsList options);

	public ProductPartViewDto toDto(ProductDefinitionPart part, OptionsList options);

	public ProductViewDto toDto(ProductFormDto form, OptionsList options);

}