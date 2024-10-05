package com.nm.products.dtos.old;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.nm.products.dtos.views.ProductPartViewDto;

/**
 * 
 * @author Nabil
 * 
 */
public class ProductCheckerContext {
	private ProductDefCache cache = new ProductDefCache();
	//
	private boolean unavailable = false;
	private Map<String, ProductPartViewDto> partsAbsent = new HashMap<String, ProductPartViewDto>();
	private Map<String, ProductPartViewDto> partsMandatory = new HashMap<String, ProductPartViewDto>();
	private Collection<ProductPartViewDto> notExistsPartAnyMore = new HashSet<ProductPartViewDto>();
	private Collection<ProductCheckerContextProductSelection> productBadSelection = new HashSet<ProductCheckerContextProductSelection>();
	private Map<String, ProductPartViewDto> badPartCardinality = new HashMap<String, ProductPartViewDto>();
	private Collection<ProductCheckerContextIngredientSelection> ingredientBadSelection = new HashSet<ProductCheckerContextIngredientSelection>();
	private boolean isInError = false;
	private boolean isInWarning = false;

	public ProductCheckerContext() {
	}

	public ProductCheckerContext(ProductDefCache cache) {
		setCache(cache);
	}

	public Map<String, ProductPartViewDto> getBadPartCardinality() {
		return badPartCardinality;
	}

	public void setBadPartCardinality(Map<String, ProductPartViewDto> badPartCardinality) {
		this.badPartCardinality = badPartCardinality;
	}

	public Map<String, ProductPartViewDto> getPartsMandatory() {
		return partsMandatory;
	}

	public void setPartsMandatory(Map<String, ProductPartViewDto> partsMandatory) {
		this.partsMandatory = partsMandatory;
	}

	public boolean isUnavailable() {
		return unavailable;
	}

	public void setUnavailable(boolean unavailable) {
		this.unavailable = unavailable;
	}

	public void computeFlag() {
		this.isInError = false;
		this.isInWarning = false;
		if (!partsAbsent.isEmpty() || !productBadSelection.isEmpty() || unavailable || !partsMandatory.isEmpty()) {
			this.isInError = true;
		}
		if (!notExistsPartAnyMore.isEmpty() || !ingredientBadSelection.isEmpty() || !badPartCardinality.isEmpty()) {
			this.isInWarning = true;
		}
	}

	public boolean isInError() {
		return isInError;
	}

	public boolean isInWarning() {
		return isInWarning;
	}

	public void setInError(boolean isInError) {
		this.isInError = isInError;
	}

	public void setInWarning(boolean isInWarning) {
		this.isInWarning = isInWarning;
	}

	public Collection<ProductCheckerContextIngredientSelection> getIngredientBadSelection() {
		return ingredientBadSelection;
	}

	public void setIngredientBadSelection(Collection<ProductCheckerContextIngredientSelection> ingredientBadSelection) {
		this.ingredientBadSelection = ingredientBadSelection;
	}

	public void setPartsAbsent(Map<String, ProductPartViewDto> partsAbsent) {
		this.partsAbsent = partsAbsent;
	}

	public Map<String, ProductPartViewDto> getPartsAbsent() {
		return partsAbsent;
	}

	public Collection<ProductCheckerContextProductSelection> getProductBadSelection() {
		return productBadSelection;
	}

	public void setProductBadSelection(Collection<ProductCheckerContextProductSelection> partBadSelection) {
		this.productBadSelection = partBadSelection;
	}

	public Collection<ProductPartViewDto> getNotExistsPartAnyMore() {
		return notExistsPartAnyMore;
	}

	public void setNotExistsPartAnyMore(Collection<ProductPartViewDto> notExistsPartAnyMore) {
		this.notExistsPartAnyMore = notExistsPartAnyMore;
	}

	public ProductDefCache getCache() {
		return cache;
	}

	public void setCache(ProductDefCache cache) {
		this.cache = cache;
		this.getPartsAbsent().clear();
		this.getPartsAbsent().putAll(getCache().getParts());
	}

	public boolean existsPart(String path) {
		return this.cache.getParts().containsKey(path);
	}

	public boolean existsProduct(String path) {
		return this.cache.getProducts().containsKey(path);
	}

	public int decrementCardinality(String path) {
		return cache.decrementCardinality(path);
	}

	public boolean existsIngredient(String path) {
		return cache.getIngredients().containsKey(path);
	}

}
