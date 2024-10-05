package com.nm.products.converter;

import java.util.Collection;

import com.nm.products.dtos.forms.ProductInstanceDto;
import com.nm.products.dtos.forms.ProductPartInstanceDto;
import com.nm.products.dtos.views.IngredientViewDto;
import com.nm.products.dtos.views.ProductPartViewDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.IngredientInstance;
import com.nm.products.model.ProductDefinition;
import com.nm.products.model.ProductDefinitionPart;
import com.nm.products.model.ProductInstance;
import com.nm.products.model.ProductInstancePart;
import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public interface ProductInstanceConverter {

	public ProductInstance convert(ProductViewDto root) throws NoDataFoundException;

	public ProductInstance convert(ProductDefinition definition, ProductInstance instance, Double price);

	public IngredientInstance convert(IngredientDefinition definition, IngredientInstance instance);

	public ProductInstancePart convert(ProductDefinitionPart part, ProductInstancePart instance);

	public ProductInstanceDto convert(ProductInstance instance, String lang, Collection<ModelOptions> options);

	public ProductInstancePart convert(ProductInstancePart part, ProductPartViewDto def);

	public IngredientViewDto convert(IngredientInstance ingredient, String currentLoc);

	public ProductPartInstanceDto convert(ProductInstancePart instance, String lang, Collection<ModelOptions> options);

}