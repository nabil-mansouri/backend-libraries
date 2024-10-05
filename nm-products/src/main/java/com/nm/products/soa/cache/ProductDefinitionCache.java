package com.nm.products.soa.cache;

import java.util.HashMap;
import java.util.Map;

import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.ProductDefinition;
import com.nm.products.model.ProductDefinitionPart;

/**
 * 
 * @author Nabil
 * 
 */
public class ProductDefinitionCache {
	private Map<Long, ProductDefinition> productsById = new HashMap<Long, ProductDefinition>();
	private Map<Long, ProductDefinitionPart> partsById = new HashMap<Long, ProductDefinitionPart>();
	private Map<Long, IngredientDefinition> ingById = new HashMap<Long, IngredientDefinition>();

	public Map<Long, IngredientDefinition> getIngById() {
		return ingById;
	}

	public void setIngById(Map<Long, IngredientDefinition> ingById) {
		this.ingById = ingById;
	}

	public Map<Long, ProductDefinitionPart> getPartsById() {
		return partsById;
	}

	public Map<Long, ProductDefinition> getProductsById() {
		return productsById;
	}

	public void setPartsById(Map<Long, ProductDefinitionPart> partsById) {
		this.partsById = partsById;
	}

	public void setProductsById(Map<Long, ProductDefinition> productsById) {
		this.productsById = productsById;
	}

}
