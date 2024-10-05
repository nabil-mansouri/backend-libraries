package com.nm.products.dtos.old;

import com.nm.products.dtos.views.IngredientViewDto;
import com.nm.products.dtos.views.ProductViewDto;

/**
 * 
 * @author Nabil
 * 
 */
public class ProductCheckerContextIngredientSelection {
	private IngredientViewDto ingredient;
	private ProductViewDto product;

	public ProductCheckerContextIngredientSelection() {
	}

	public ProductCheckerContextIngredientSelection(IngredientViewDto ingredient, ProductViewDto product) {
		super();
		this.ingredient = ingredient;
		this.product = product;
	}

	public IngredientViewDto getIngredient() {
		return ingredient;
	}

	public void setIngredient(IngredientViewDto ingredient) {
		this.ingredient = ingredient;
	}

	public ProductViewDto getProduct() {
		return product;
	}

	public void setProduct(ProductViewDto product) {
		this.product = product;
	}

}
