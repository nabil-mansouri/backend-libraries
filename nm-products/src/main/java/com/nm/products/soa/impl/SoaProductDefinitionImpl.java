package com.nm.products.soa.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nm.app.draft.DraftDto;
import com.nm.app.draft.SoaDraft;
import com.nm.cms.constants.CmsOptions;
import com.nm.products.constants.ProductState;
import com.nm.products.constants.ProductType;
import com.nm.products.converter.ProductDefinitionFormConverter;
import com.nm.products.converter.ProductDefinitionViewConverter;
import com.nm.products.dao.DaoCategory;
import com.nm.products.dao.DaoIngredient;
import com.nm.products.dao.DaoProductDefinition;
import com.nm.products.dao.impl.CategoryQueryBuilder;
import com.nm.products.dao.impl.IngredientQueryBuilder;
import com.nm.products.dao.impl.ProductDefinitionQueryBuilder;
import com.nm.products.dtos.forms.CategoryFormDto;
import com.nm.products.dtos.forms.IngredientFormDto;
import com.nm.products.dtos.forms.ProductFormDto;
import com.nm.products.dtos.options.DraftTypeProduct;
import com.nm.products.dtos.options.ProductFormOptions;
import com.nm.products.dtos.views.CategoryViewDto;
import com.nm.products.dtos.views.IngredientViewDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.model.Category;
import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.ProductDefinition;
import com.nm.products.soa.SoaProductDefinition;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.graphs.DefaultGraphPathBuilder;
import com.nm.utils.graphs.GraphPathBuilder;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public class SoaProductDefinitionImpl implements SoaProductDefinition {
	protected Log log = LogFactory.getLog(getClass());
	//
	private DaoCategory daoCategory;
	private DaoProductDefinition daoProductDefinition;
	private DaoIngredient daoIngredient;
	private SoaDraft soaDraft;
	private ProductDefinitionViewConverter productConverter;
	private ProductDefinitionFormConverter formConverter;

	public void setDaoCategory(DaoCategory daoCategory) {
		this.daoCategory = daoCategory;
	}

	public void setDaoIngredient(DaoIngredient daoIngredient) {
		this.daoIngredient = daoIngredient;
	}

	public void setDaoProductDefinition(DaoProductDefinition daoProductDefinition) {
		this.daoProductDefinition = daoProductDefinition;
	}

	public void setFormConverter(ProductDefinitionFormConverter formConverter) {
		this.formConverter = formConverter;
	}

	public void setProductConverter(ProductDefinitionViewConverter productConverter) {
		this.productConverter = productConverter;
	}

	public void setSoaDraft(SoaDraft soaDraft) {
		this.soaDraft = soaDraft;
	}

	public CategoryFormDto createCategory() {
		CategoryFormDto form = formConverter.toDto(new Category(),
				new OptionsList().withOption(ProductFormOptions.values()).withOption(CmsOptions.FullForm));
		return form;
	}

	public CategoryFormDto createCategory(Long parent) {
		CategoryFormDto form = formConverter.toDto(new Category(),
				new OptionsList().withOption(ProductFormOptions.values()).withOption(CmsOptions.FullForm));
		form.setParent(new CategoryFormDto(parent));
		return form;
	}

	public ProductViewDto setProductState(Long id, ProductState state, OptionsList options)
			throws NoDataFoundException {
		ProductDefinition def = daoProductDefinition.get(id);
		def.setState(state);
		def.setStateDate(new Date());
		return this.productConverter.toDto(def, options);
	}

	public IngredientFormDto createIngredient() {
		IngredientFormDto form = formConverter.toDto(new IngredientDefinition(),
				new OptionsList().withOption(ProductFormOptions.values()).withOption(CmsOptions.FullForm));
		return form;
	}

	public ProductFormDto createProduct() {
		ProductFormDto form = formConverter.toDto(new ProductDefinition(),
				new OptionsList().withOption(ProductFormOptions.values()).withOption(CmsOptions.FullForm));
		form.setType(ProductType.Product);
		return form;
	}

	//
	public CategoryFormDto editCategory(Long id) throws NoDataFoundException {
		Category category = daoCategory.get(id);
		return formConverter.toDto(category,
				new OptionsList().withOption(ProductFormOptions.values()).withOption(CmsOptions.FullForm));
	}

	public ProductFormDto editDraft(Long id, OptionsList options) throws NoDataFoundException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		DraftDto draft = soaDraft.getDraft(id);
		ProductFormDto form = mapper.readValue(draft.getPayload(), ProductFormDto.class);
		form.setIdDraft(draft.getId());
		return form;
	}

	public IngredientFormDto editIngredient(Long id) throws NoDataFoundException {
		IngredientDefinition ingredient = daoIngredient.get(id);
		return formConverter.toDto(ingredient,
				new OptionsList().withOption(ProductFormOptions.values()).withOption(CmsOptions.FullForm));
	}

	@Transactional
	public ProductFormDto editProduct(Long id) throws NoDataFoundException {
		ProductDefinition product = daoProductDefinition.get(id);
		return formConverter.toDto(product,
				new OptionsList().withOption(ProductFormOptions.values()).withOption(CmsOptions.FullForm));
	}

	@Transactional
	public Collection<CategoryViewDto> fetch(CategoryQueryBuilder query, OptionsList options) {
		Collection<CategoryViewDto> response = new ArrayList<CategoryViewDto>();
		Collection<Category> categories = daoCategory.find(query);
		for (Category category : categories) {
			response.add(productConverter.toDto(category, options));
		}
		return response;
	}

	@Transactional
	public Collection<IngredientViewDto> fetch(IngredientQueryBuilder query, OptionsList options) {
		Collection<IngredientViewDto> response = new ArrayList<IngredientViewDto>();
		Collection<IngredientDefinition> ingredients = daoIngredient.find(query);
		for (IngredientDefinition ingredient : ingredients) {
			response.add(productConverter.toDto(ingredient, options));
		}
		return response;
	}

	@Transactional(readOnly = true)
	public ProductViewDto fetch(Long id, OptionsList options) {
		return fetch(ProductDefinitionQueryBuilder.get().withId(id), options).iterator().next();
	}

	@Transactional
	public Collection<ProductViewDto> fetch(ProductDefinitionQueryBuilder query, OptionsList options) {
		Collection<ProductViewDto> response = new ArrayList<ProductViewDto>();
		Collection<ProductDefinition> products = daoProductDefinition.find(query);
		for (ProductDefinition product : products) {
			response.add(productConverter.toDto(product, options));
		}
		return response;
	}

	public Collection<ProductViewDto> fetchDrafts(OptionsList options) {
		ObjectMapper mapper = new ObjectMapper();
		Collection<DraftDto> drafts = soaDraft.getDrafts(DraftTypeProduct.Product);
		Collection<ProductFormDto> forms = new ArrayList<ProductFormDto>();
		for (DraftDto d : drafts) {
			try {
				ProductFormDto form = mapper.readValue(d.getPayload(), ProductFormDto.class);
				form.setIdDraft(d.getId());
				forms.add(form);
			} catch (Exception e) {
				log.error("Fail to transform draft", e);
			}
		}
		Collection<ProductViewDto> beans = new ArrayList<ProductViewDto>();
		for (ProductFormDto form : forms) {
			beans.add(productConverter.toDto(form, options));
		}
		return beans;
	}

	@Transactional(readOnly = true)
	public GraphPathBuilder getPathBuilder() {
		return new DefaultGraphPathBuilder();
	}

	public ProductFormDto refresh(ProductFormDto form, OptionsList withOption) {
		return formConverter.toDto(form, withOption);
	}

	@Transactional
	public Category removeCategory(Long idCategory) {
		Category oldProduct = null;
		try {
			oldProduct = daoCategory.get(idCategory);
			daoCategory.delete(oldProduct);
		} catch (NoDataFoundException e) {
			// Already deleted
		}
		return oldProduct;
	}

	@Transactional
	public IngredientDefinition removeIngredient(Long idIngredient) {
		IngredientDefinition oldProduct = null;
		try {
			oldProduct = daoIngredient.get(idIngredient);
			daoIngredient.delete(oldProduct);
		} catch (NoDataFoundException e) {
			// Already deleted
		}
		return oldProduct;
	}

	@Transactional
	public ProductDefinition removeProduct(Long idProductDef) {
		ProductDefinition product = null;
		try {
			product = daoProductDefinition.get(idProductDef);
			daoProductDefinition.delete(product);
		} catch (NoDataFoundException e) {
			// Already deleted
		}
		return product;
	}

	@Transactional
	public CategoryViewDto saveOrUpdate(CategoryFormDto form, OptionsList options) throws NoDataFoundException {
		Category category = formConverter.toEntity(form, options);
		daoCategory.saveOrUpdate(category);
		if (form.getParent() != null && form.getParent().getId() != null) {
			daoCategory.get(form.getParent().getId()).add(category);
		}
		//
		form.setId(category.getId());
		return productConverter.toDto(category, options);
	}

	@Transactional
	public IngredientViewDto saveOrUpdate(IngredientFormDto form, OptionsList options) throws NoDataFoundException {
		IngredientDefinition ingredient = formConverter.toEntity(form, options);
		daoIngredient.saveOrUpdate(ingredient);
		//
		form.setId(ingredient.getId());
		return productConverter.toDto(ingredient, options);
	}

	@Transactional
	public ProductViewDto saveOrUpdate(ProductFormDto form, OptionsList options) throws NoDataFoundException {
		ProductDefinition product = formConverter.toEntity(form, options);
		//
		daoProductDefinition.saveOrUpdate(product);
		if (form.getIdDraft() != null) {
			soaDraft.remove(form.getIdDraft());
		}
		//
		form.setId(product.getId());
		return productConverter.toDto(product, options);
	}

	public ProductFormDto saveOrUpdateDraft(ProductFormDto form) throws IOException, NoDataFoundException {
		ObjectMapper mapper = new ObjectMapper();
		if (form.getIdDraft() != null) {
			DraftDto draft = soaDraft.update(DraftTypeProduct.Product, mapper.writeValueAsString(form),
					form.getIdDraft());
			form.setIdDraft(draft.getId());
		} else {
			DraftDto draft = soaDraft.saveDraft(DraftTypeProduct.Product, mapper.writeValueAsString(form));
			form.setIdDraft(draft.getId());
		}
		return form;
	}

	public void removeDraft(Long id) {
		soaDraft.remove(id);
	}
}
