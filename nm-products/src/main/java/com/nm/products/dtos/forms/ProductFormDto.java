package com.nm.products.dtos.forms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.cms.dtos.CmsDtoContentsComposedForm;
import com.nm.cms.dtos.CmsDtoContentsTextForm;
import com.nm.datas.dtos.AppDataDtoImpl;
import com.nm.products.constants.ProductType;
import com.nm.products.dtos.views.CategoryViewDto;
import com.nm.products.dtos.views.IngredientViewDto;
import com.nm.products.dtos.views.ProductViewDto;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductFormDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Long id;
	private ProductType type;
	private CmsDtoContentsComposedForm cms = new CmsDtoContentsComposedForm();
	private Boolean hasProducts = false;
	private Boolean hasIngredients = false;
	private ProductPartFormDto selectedPart;
	private List<IngredientViewDto> ingredients = new ArrayList<IngredientViewDto>();
	private List<CategoryTreeDto> categories = new ArrayList<CategoryTreeDto>();
	private List<ProductPartFormDto> parts = new ArrayList<ProductPartFormDto>();
	private Map<Long, ProductViewDto> products = new HashMap<Long, ProductViewDto>();

	private Long idDraft = null;

	public boolean isConfigError() {
		return cms.isNoDefaultLang() || cms.isNoSelectedLang();
	}

	public ProductFormDto setConfigError(boolean configError) {
		return this;
	}

	public ProductFormDto select(CategoryViewDto cat) {
		CategoryTreeDto c = new CategoryTreeDto();
		c.setId(cat.getId());
		c.setSelected(true);
		this.categories.add(c);
		return this;
	}

	public ProductFormDto clearPart() {
		this.parts.clear();
		return this;
	}

	public ProductPartFormDto pushPart() {
		this.setHasProducts(true);
		ProductPartFormDto n = new ProductPartFormDto();
		this.parts.add(n);
		return n;
	}

	public ProductFormDto select(IngredientViewDto cat) {
		setHasIngredients(true);
		this.ingredients.add(cat.clone().setSelected(true));
		return this;
	}

	public ProductFormDto select(IngredientViewDto cat, boolean facultatif) {
		setHasIngredients(true);
		this.ingredients.add(cat.clone().setSelected(true).setFacultatif(facultatif));
		return this;
	}

	public ProductFormDto add(CmsDtoContentsTextForm e) {
		cms.add(e);
		return this;
	}

	public ProductFormDto add(IngredientViewDto e) {
		ingredients.add(e);
		return this;
	}

	public ProductFormDto add(IngredientFormDto ing1) {
		IngredientViewDto c = new IngredientViewDto();
		c.setId(ing1.getId());
		add(c);
		return this;
	}

	public Map<Long, ProductViewDto> getProducts() {
		return products;
	}

	public ProductFormDto setProducts(Map<Long, ProductViewDto> products) {
		this.products = products;
		return this;
	}

	public ProductFormDto add(ProductPartFormDto part) {
		getParts().add(part);
		setHasProducts(true);
		return this;
	}

	public ProductFormDto add(AppDataDtoImpl e) {
		this.cms.add(e);
		return this;
	}

	public ProductFormDto addParts(List<ProductPartFormDto> parts) {
		this.parts = parts;
		setHasProducts(!this.parts.isEmpty());
		return this;
	}

	public List<CategoryTreeDto> getCategories() {
		return categories;
	}

	public CmsDtoContentsComposedForm getCms() {
		return cms;
	}

	public ProductType getType() {
		return type;
	}

	public Boolean getHasIngredients() {
		return hasIngredients;
	}

	public Boolean getHasProducts() {
		return hasProducts;
	}

	public Long getId() {
		return id;
	}

	public Long getIdDraft() {
		return idDraft;
	}

	public List<IngredientViewDto> getIngredients() {
		return ingredients;
	}

	public List<ProductPartFormDto> getParts() {
		return parts;
	}

	public ProductPartFormDto getSelectedPart() {
		return selectedPart;
	}

	public ProductFormDto setCategories(List<CategoryTreeDto> categories) {
		this.categories = categories;
		return this;
	}

	public ProductFormDto setCms(CmsDtoContentsComposedForm contents) {
		this.cms = contents;
		return this;
	}

	public ProductFormDto setType(ProductType flag) {
		this.type = flag;
		return this;
	}

	public ProductFormDto setHasIngredients(Boolean hasIngredients) {
		this.hasIngredients = hasIngredients;
		return this;
	}

	public ProductFormDto setHasProducts(Boolean hasProducts) {
		this.hasProducts = hasProducts;
		return this;
	}

	public ProductFormDto setId(Long id) {
		this.id = id;
		return this;
	}

	public ProductFormDto setIdDraft(Long idDraft) {
		this.idDraft = idDraft;
		return this;
	}

	public ProductFormDto setIngredients(List<IngredientViewDto> ingredients) {
		this.ingredients = ingredients;
		return this;
	}

	public ProductFormDto setSelectedPart(ProductPartFormDto selectedPart) {
		this.selectedPart = selectedPart;
		return this;
	}

	public ProductFormDto setImg(AppDataDtoImpl appDataDtoImpl) {
		this.cms.setImg(appDataDtoImpl);
		return this;
	}

	public ProductFormDto setParts(List<ProductPartFormDto> parts) {
		this.parts = parts;
		return this;
	}

	public ProductFormDto put(Long key, ProductViewDto value) {
		products.put(key, value);
		return this;
	}

	public ProductFormDto add(CategoryTreeDto tree) {
		categories.add(tree);
		return this;
	}

	public ProductFormDto add(CategoryViewDto categoryDto) {
		CategoryTreeDto tree = new CategoryTreeDto();
		tree.setId(categoryDto.getId());
		categories.add(tree);
		return this;
	}

}
