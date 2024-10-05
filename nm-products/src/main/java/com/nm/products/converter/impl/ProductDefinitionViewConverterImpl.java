package com.nm.products.converter.impl;

import com.nm.cms.dtos.CmsDtoContentsComposedView;
import com.nm.cms.models.CmsContentsComposed;
import com.nm.products.converter.CategoryTreeListener;
import com.nm.products.converter.ProductDefinitionViewConverter;
import com.nm.products.dtos.forms.CategoryTreeDto;
import com.nm.products.dtos.forms.ProductFormDto;
import com.nm.products.dtos.options.CategoryOptions;
import com.nm.products.dtos.options.ProductOptions;
import com.nm.products.dtos.views.CategoryViewDto;
import com.nm.products.dtos.views.IngredientViewDto;
import com.nm.products.dtos.views.ProductPartViewDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.model.Category;
import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.ProductDefinition;
import com.nm.products.model.ProductDefinitionIngredient;
import com.nm.products.model.ProductDefinitionPart;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NotFoundException;


/**
 * 
 * @author Nabil
 * 
 */
public class ProductDefinitionViewConverterImpl implements ProductDefinitionViewConverter {

	private DtoConverterRegistry registry;
	private DtoConverter<CmsDtoContentsComposedView, CmsContentsComposed> converters;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	private DtoConverter<CmsDtoContentsComposedView, CmsContentsComposed> getConverter() throws NotFoundException {
		if (this.converters == null) {
			converters = registry.search(CmsDtoContentsComposedView.class, CmsContentsComposed.class);
		}
		return this.converters;
	}

	public CategoryViewDto toDto(Category category, OptionsList options) {
		return toDto(new CategoryViewDto(), category, options);
	}

	public CategoryViewDto toDto(CategoryViewDto view, Category category, OptionsList options) {
		try {
			view.setCms(getConverter().toDto(category.getContent(), options));
			view.setId(category.getId());
			return view;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public IngredientViewDto toDto(ProductDefinitionIngredient ingredient, OptionsList options) {
		IngredientViewDto view = toDto(ingredient.getIngredient(), options);
		view.setFacultatif(!ingredient.getMandatory());
		return view;
	}

	protected CategoryTreeDto toTreeDtoDoIt(Category category, OptionsList options, CategoryTreeListener... listeners) {
		CategoryTreeDto tree = new CategoryTreeDto();
		toDto(tree, category, options);
		//
		for (Category child : category.getChildren()) {
			tree.getChildrens().add(toTreeDtoDoIt(child, options, listeners));
		}
		if (options.contains(CategoryOptions.ProductCount)) {
			tree.setCountProducts(category.getProductDefinitions().size());
		}
		for (CategoryTreeListener l : listeners) {
			l.onCategory(category, tree);
		}
		return tree;
	}

	public CategoryTreeDto toTreeDto(Category category, OptionsList options, CategoryTreeListener... listeners) {
		CategoryTreeDto tree = toTreeDtoDoIt(category, options, listeners);
		tree.computeLevelRecursively();
		tree.sumCounts();
		return tree;
	}

	public CategoryTreeDto toTreeDto(Category category, OptionsList options) {
		return toTreeDto(category, options, new CategoryTreeListener[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rm.soa.products.converter.ProductDefinitionConverter#convert(com.rm.
	 * model.products.IngredientDefinition, java.lang.String)
	 */

	public IngredientViewDto toDto(IngredientDefinition ingredient, OptionsList options) {
		try {
			IngredientViewDto view = new IngredientViewDto();
			view.setCms(getConverter().toDto(ingredient.getContent(), options));
			view.setId(ingredient.getId());
			return view;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public ProductViewDto toDto(ProductDefinition product, OptionsList options) {
		try {
			ProductViewDto view = new ProductViewDto();
			view.setId(product.getId());
			view.setState(product.getState());
			view.setDateState(product.getStateDate());
			view.setCms(getConverter().toDto(product.getContent(), options));
			//
			if (options.contains(ProductOptions.Categories)) {
				for (Category category : product.getCategories()) {
					view.addCategories(toDto(category, options));
				}
			}
			//
			view.setCreatedAt(product.getCreated());
			if (options.contains(ProductOptions.Ingredients)) {
				// for (ProductDefinitionIngredient ing :
				// product.getIngredients())
				// {
				// IngredientViewDto ingView = toDto(ing, options);
				// view.add(ingView);
				// }
			}
			if (options.contains(ProductOptions.Parts)) {
				// for (ProductDefinitionPart part : product.getParts()) {
				// view.add(toDto(part, options));
				// }
			}
			return view;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public ProductPartViewDto toDto(ProductDefinitionPart part, OptionsList options) {
		ProductPartViewDto partView = new ProductPartViewDto();
		partView.setId(part.getId());
		partView.setMandatory(part.getMandatory());
		partView.setName(part.getName());
		if (options.contains(ProductOptions.ProductsPart)) {
			// for (ProductDefinition child : part.getProducts()) {
			// partView.add(toDto(child, options));
			// }
		}
		return partView;
	}

	public ProductViewDto toDto(ProductFormDto form, OptionsList options) {
		try {
			ProductViewDto view = new ProductViewDto();
			view.setIdDraft(form.getIdDraft());
			CmsDtoContentsComposedView viewCm = registry.search(CmsDtoContentsComposedView.class, form.getCms())
					.toDto(form.getCms(), options);
			view.setCms(viewCm);
			return view;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
