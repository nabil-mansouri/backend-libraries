package com.nm.products.converter.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.BooleanUtils;

import com.nm.cms.dtos.CmsDtoContentsComposedForm;
import com.nm.cms.models.CmsContentsComposed;
import com.nm.products.converter.CategoryTreeListener;
import com.nm.products.converter.ProductDefinitionFormConverter;
import com.nm.products.converter.ProductDefinitionViewConverter;
import com.nm.products.dao.DaoCategory;
import com.nm.products.dao.DaoIngredient;
import com.nm.products.dao.DaoProductDefinition;
import com.nm.products.dao.impl.CategoryQueryBuilder;
import com.nm.products.dao.impl.IngredientQueryBuilder;
import com.nm.products.dao.impl.ProductDefinitionQueryBuilder;
import com.nm.products.dtos.forms.CategoryFormDto;
import com.nm.products.dtos.forms.CategoryTreeDto;
import com.nm.products.dtos.forms.IngredientFormDto;
import com.nm.products.dtos.forms.ProductFormDto;
import com.nm.products.dtos.forms.ProductPartFormDto;
import com.nm.products.dtos.options.ProductFormOptions;
import com.nm.products.dtos.views.IngredientViewDto;
import com.nm.products.model.Category;
import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.ProductDefinition;
import com.nm.products.model.ProductDefinitionIngredient;
import com.nm.products.model.ProductDefinitionPart;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.graphs.IGraph;
import com.nm.utils.graphs.iterators.GraphIteratorBuilder;
import com.nm.utils.graphs.listeners.IteratorListenerIGraph;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.NotFoundException;


/**
 * 
 * @author Nabil
 * 
 */
public class ProductDefinitionFormConverterImpl implements ProductDefinitionFormConverter {

	private ProductDefinitionViewConverter viewConverter;
	private DaoCategory daoCategory;
	private DaoProductDefinition daoProductDefinition;
	private DaoIngredient daoIngredient;
	private DtoConverterRegistry registry;
	private DtoConverter<CmsDtoContentsComposedForm, CmsContentsComposed> converters;

	public void setDaoCategory(DaoCategory daoCategory) {
		this.daoCategory = daoCategory;
	}

	public void setDaoIngredient(DaoIngredient daoIngredient) {
		this.daoIngredient = daoIngredient;
	}

	public void setDaoProductDefinition(DaoProductDefinition daoProductDefinition) {
		this.daoProductDefinition = daoProductDefinition;
	}

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public void setViewConverter(ProductDefinitionViewConverter viewConverter) {
		this.viewConverter = viewConverter;
	}

	private DtoConverter<CmsDtoContentsComposedForm, CmsContentsComposed> getConverter() throws NotFoundException {
		if (this.converters == null) {
			converters = registry.search(CmsDtoContentsComposedForm.class, CmsContentsComposed.class);
		}
		return this.converters;
	}

	public CategoryFormDto toDto(Category category, OptionsList options) {
		CategoryFormDto form = new CategoryFormDto();
		try {
			form.setCms(getConverter().toDto(category.getContent(), options));
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		form.setId(category.getId());
		if (category.getId() != null) {
			Collection<Category> categories = daoCategory
					.find(CategoryQueryBuilder.get().withChild(category.getId()).getQuery());
			if (!categories.isEmpty()) {
				form.setParent(new CategoryFormDto(categories.iterator().next().getId()));
			}
		}
		return form;
	}

	public IngredientFormDto toDto(IngredientDefinition ingredient, OptionsList options) {
		IngredientFormDto form = new IngredientFormDto();
		try {
			form.setCms(getConverter().toDto(ingredient.getContent(), options));
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		form.setId(ingredient.getId());
		return form;
	}

	private void _refreshIngredients(ProductFormDto form, OptionsList options, Collection<Long> idSelected) {
		form.setHasIngredients(!idSelected.isEmpty());
		Collection<IngredientDefinition> all = daoIngredient.find(IngredientQueryBuilder.get());
		form.getIngredients().clear();
		for (IngredientDefinition ing : all) {
			form.add(viewConverter.toDto(ing, options).setSelected(idSelected.contains(ing.getId())));
		}
	}

	private void _refreshParts(ProductFormDto form, OptionsList options) {
		ProductDefinitionQueryBuilder query = ProductDefinitionQueryBuilder.get();
		if (form.getId() != null) {
			query.withNotId(form.getId());
		}
		Collection<ProductDefinition> all = daoProductDefinition.find(query);
		Collection<Long> idAvail = new HashSet<Long>();
		form.getProducts().clear();
		for (ProductDefinition prod : all) {
			idAvail.add(prod.getId());
			form.put(prod.getId(), viewConverter.toDto(prod, options));
		}
		for (ProductPartFormDto part : form.getParts()) {
			Set<Long> intersection = new HashSet<Long>(idAvail);
			intersection.retainAll(part.getProducts().keySet());
			part.clearAll();
			part.selectAll(intersection);
		}
		// At least one part
		if (form.getParts().isEmpty()) {
			form.getParts().add(new ProductPartFormDto());
		}
		form.getParts().iterator().next().setSelected(true);
	}

	private void _refreshCategories(ProductFormDto form, OptionsList options, final Collection<Long> idSelected) {
		Collection<Category> categories = daoCategory.find(CategoryQueryBuilder.get().withNoParent());
		form.getCategories().clear();
		for (Category category : categories) {
			CategoryTreeDto tree = viewConverter.toTreeDto(category, options, new CategoryTreeListener() {

				public void onCategory(Category category, CategoryTreeDto tree) {
					tree.setSelected(idSelected.contains(category.getId()));
				}
			});
			form.add(tree);
		}
	}

	public ProductFormDto toDto(ProductFormDto form, OptionsList options) {
		if (options.contains(ProductFormOptions.Ingredients)) {
			Collection<Long> idSelected = new HashSet<Long>();
			for (IngredientViewDto ing : form.getIngredients()) {
				if (ing.isSelected()) {
					idSelected.add(ing.getId());
				}
			}
			_refreshIngredients(form, options, idSelected);
		}
		//
		if (options.contains(ProductFormOptions.Parts)) {
			_refreshParts(form, options);
		}
		//
		if (options.contains(ProductFormOptions.Categories)) {
			final Collection<Long> idSelected = new HashSet<Long>();
			for (CategoryTreeDto tree : form.getCategories()) {
				GraphIteratorBuilder.buildDeep().iterate(tree, new IteratorListenerIGraph() {

					public void onFounded(IGraph node) {
						CategoryTreeDto bean = (CategoryTreeDto) node;
						if (bean.isSelected()) {
							idSelected.add(bean.getId());
						}
					}
				});
			}
			_refreshCategories(form, options, idSelected);
		}
		return form;
	}

	public ProductFormDto toDto(ProductDefinition product, OptionsList options) {
		ProductFormDto form = new ProductFormDto();
		form.setType(product.getType());
		//
		if (options.contains(ProductFormOptions.Cms)) {
			form.setId(product.getId());
			try {
				form.setCms(getConverter().toDto(product.getContent(), options));
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		}
		//
		if (options.contains(ProductFormOptions.Ingredients)) {
			Collection<Long> idSelected = new HashSet<Long>();
			Map<Long, Boolean> mandatories = new HashMap<Long, Boolean>();
			for (ProductDefinitionIngredient ingredient : product.getIngredients()) {
				idSelected.add(ingredient.getIngredientId());
				mandatories.put(ingredient.getIngredientId(), ingredient.getMandatory());
			}
			_refreshIngredients(form, options, idSelected);
			for (IngredientViewDto view : form.getIngredients()) {
				if (mandatories.containsKey(view.getId())) {
					view.setFacultatif(!mandatories.get(view.getId()));
				}
			}
		}
		//
		if (options.contains(ProductFormOptions.Parts)) {
			form.setHasProducts(!product.getParts().isEmpty());
			for (ProductDefinitionPart part : product.getParts()) {
				form.add(toDto(part, options));
			}
			_refreshParts(form, options);
		}
		//
		if (options.contains(ProductFormOptions.Categories)) {
			// Categories
			{
				final Collection<Long> selected = new HashSet<Long>();
				for (Category c : product.getCategories()) {
					selected.add(c.getId());
				}
				_refreshCategories(form, options, selected);
			}
		}
		return form;
	}

	public ProductPartFormDto toDto(ProductDefinitionPart part, OptionsList options) {
		ProductPartFormDto form = new ProductPartFormDto();
		form.setId(part.getId()).setName(part.getName()).withMandatory(part.getMandatory());
		for (ProductDefinition subProduct : part.getProducts()) {
			form.add(viewConverter.toDto(subProduct, options).setSelected(true));
		}
		return form;
	}

	public Category toEntity(CategoryFormDto form, OptionsList options) throws NoDataFoundException {
		Category category = new Category();
		if (form.getId() != null) {
			category = daoCategory.get(form.getId());
		}
		try {
			category.setContent(getConverter().toEntity(form.getCms(), options));
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return category;
	}

	public IngredientDefinition toEntity(IngredientFormDto form, OptionsList options) throws NoDataFoundException {
		IngredientDefinition ingredient = new IngredientDefinition();
		if (form.getId() != null) {
			ingredient = daoIngredient.get(form.getId());
		}
		try {
			ingredient.setContent(getConverter().toEntity(form.getCms(), options));
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return ingredient;
	}

	public ProductDefinition toEntity(ProductFormDto form, OptionsList options) throws NoDataFoundException {
		ProductDefinition product = new ProductDefinition();
		if (form.getId() != null) {
			product = daoProductDefinition.get(form.getId());
		}
		final List<Long> catIds = new ArrayList<Long>();
		for (CategoryTreeDto cat : form.getCategories()) {
			GraphIteratorBuilder.buildDeep().iterate(cat, new IteratorListenerIGraph() {

				public void onFounded(IGraph node) {
					CategoryTreeDto n = (CategoryTreeDto) node;
					if (n.isSelected()) {
						catIds.add(n.getId());
					}
				}
			});
		}
		product.setType(form.getType());
		product.setCategories(daoCategory.findByIds(catIds));
		//
		try {
			product.setContent(getConverter().toEntity(form.getCms(), options));
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		//
		product.getIngredients().clear();
		if (form.getHasIngredients()) {
			for (IngredientViewDto ing : form.getIngredients()) {
				if (ing.isSelected()) {
					product.addIngredient(
							new ProductDefinitionIngredient(!ing.getFacultatif(), daoIngredient.load(ing.getId())));
				}
			}
		}
		//
		product.getParts().clear();
		if (form.getHasProducts()) {
			for (ProductPartFormDto part : form.getParts()) {
				product.addPart(toEntity(part, options));
			}
		}
		return product;
	}

	public ProductDefinitionPart toEntity(ProductPartFormDto part, OptionsList options) {
		ProductDefinitionPart partDef = new ProductDefinitionPart();
		partDef.setName(part.getName());
		Boolean facultatif = BooleanUtils.toBooleanObject(part.getFacultatif(), 1, 0, 0);
		partDef.setMandatory(!facultatif);
		//
		partDef.getProducts().clear();
		for (Long p : part.getProducts().keySet()) {
			if (part.getProducts().get(p)) {
				// MUST DO LIKE THAT IN ORDER TO INSTANTE NEW OBJECT FOR
				// DUPLICATES
				partDef.addProductDefinition(daoProductDefinition.load(p));
			}
		}
		return partDef;
	}

}
