package com.nm.products.dtos.old;

import java.util.HashMap;
import java.util.Map;

import com.nm.products.dtos.views.IngredientViewDto;
import com.nm.products.dtos.views.ProductPartViewDto;
import com.nm.products.dtos.views.ProductViewDto;

/**
 * 
 * @author Nabil
 * 
 */
public class ProductDefCache {
	private Map<String, ProductPartViewDto> parts = new HashMap<String, ProductPartViewDto>();
	private Map<String, ProductViewDto> products = new HashMap<String, ProductViewDto>();
	private Map<String, IngredientViewDto> ingredients = new HashMap<String, IngredientViewDto>();
	private Map<String, Integer> partsCardinality = new HashMap<String, Integer>();

	public Map<String, Integer> getPartsCardinality() {
		return partsCardinality;
	}

	public void setPartsCardinality(Map<String, Integer> partsCardinality) {
		this.partsCardinality = partsCardinality;
	}

	public int incrementCardinality(String path) {
		if (!partsCardinality.containsKey(path)) {
			partsCardinality.put(path, 0);
		}
		int card = partsCardinality.get(path);
		card++;
		partsCardinality.put(path, card);
		return card;
	}

	public int decrementCardinality(String path) {
		if (partsCardinality.containsKey(path)) {
			int card = partsCardinality.get(path);
			card--;
			if (card > 0) {
				partsCardinality.put(path, card);
			} else {
				partsCardinality.remove(path);
			}
			return card;
		} else {
			return -1;
		}

	}

	public Map<String, ProductPartViewDto> getParts() {
		return parts;
	}

	public void setParts(Map<String, ProductPartViewDto> parts) {
		this.parts = parts;
	}

	public Map<String, ProductViewDto> getProducts() {
		return products;
	}

	public void setProducts(Map<String, ProductViewDto> products) {
		this.products = products;
	}

	public Map<String, IngredientViewDto> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Map<String, IngredientViewDto> ingredients) {
		this.ingredients = ingredients;
	}

	public void put(String path, ProductViewDto node) {
		this.products.put(path, node);
	}

	public void put(String path, ProductPartViewDto node) {
		this.parts.put(path, node);
	}

	public void put(String path, IngredientViewDto node) {
		this.ingredients.put(path, node);
	}

}
