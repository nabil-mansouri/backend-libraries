package com.nm.products.soa;

import java.io.IOException;
import java.util.Collection;

import com.nm.products.constants.ProductState;
import com.nm.products.dao.impl.CategoryQueryBuilder;
import com.nm.products.dao.impl.IngredientQueryBuilder;
import com.nm.products.dao.impl.ProductDefinitionQueryBuilder;
import com.nm.products.dtos.forms.CategoryFormDto;
import com.nm.products.dtos.forms.IngredientFormDto;
import com.nm.products.dtos.forms.ProductFormDto;
import com.nm.products.dtos.views.CategoryViewDto;
import com.nm.products.dtos.views.IngredientViewDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.model.Category;
import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.ProductDefinition;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.graphs.GraphPathBuilder;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public interface SoaProductDefinition {
	public CategoryFormDto createCategory();

	public CategoryFormDto createCategory(Long parent);

	public IngredientFormDto createIngredient();

	public ProductFormDto createProduct();

	public CategoryFormDto editCategory(Long id) throws NoDataFoundException;

	public ProductFormDto editDraft(Long id, OptionsList options) throws NoDataFoundException, IOException;

	public IngredientFormDto editIngredient(Long id) throws NoDataFoundException;

	public ProductFormDto editProduct(Long id) throws NoDataFoundException;

	public Collection<CategoryViewDto> fetch(CategoryQueryBuilder query, OptionsList options);

	public Collection<IngredientViewDto> fetch(IngredientQueryBuilder query, OptionsList options);

	public ProductViewDto fetch(Long id, OptionsList options);

	public Collection<ProductViewDto> fetch(ProductDefinitionQueryBuilder query, OptionsList options);

	public Collection<ProductViewDto> fetchDrafts(OptionsList options);

	public GraphPathBuilder getPathBuilder();

	public ProductFormDto refresh(ProductFormDto form, OptionsList withOption);

	public Category removeCategory(Long idCategory);

	public void removeDraft(Long id);

	public IngredientDefinition removeIngredient(Long idIngredient);

	public ProductDefinition removeProduct(Long idProductDef);

	public CategoryViewDto saveOrUpdate(CategoryFormDto category, OptionsList options) throws NoDataFoundException;

	public IngredientViewDto saveOrUpdate(IngredientFormDto ingredient, OptionsList options)
			throws NoDataFoundException;

	public ProductViewDto saveOrUpdate(ProductFormDto product, OptionsList options) throws NoDataFoundException;

	public ProductFormDto saveOrUpdateDraft(ProductFormDto product) throws IOException, NoDataFoundException;

	public ProductViewDto setProductState(Long id, ProductState state, OptionsList options)
			throws NoDataFoundException;

}
