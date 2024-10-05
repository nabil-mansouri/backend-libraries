package com.nm.products.dtos.navigation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;
import com.nm.products.constants.NavigationBodyState;
import com.nm.products.dtos.views.IngredientViewDto;
import com.nm.products.dtos.views.ProductViewDto;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class NavigationBody implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Collection<ProductViewDto> products = new ArrayList<ProductViewDto>();
	private Collection<IngredientViewDto> ingredients = new ArrayList<IngredientViewDto>();
	private ProductViewDto product;
	private IngredientViewDto ingredient;
	private NavigationBodyState state = NavigationBodyState.Begin;
	private boolean required = true;

	public boolean containsIngredient(Long id) {
		for (IngredientViewDto i : this.getIngredients()) {
			if (Objects.equal(id, i.getId())) {
				return true;
			}
		}
		return false;
	}

	public boolean containsProduct(Long id) {
		for (ProductViewDto i : this.getProducts()) {
			if (Objects.equal(id, i.getId())) {
				return true;
			}
		}
		return false;
	}

	public void clearAll() {
		this.product = null;
		this.ingredient = null;
		this.state = NavigationBodyState.Begin;
		this.products.clear();
		this.ingredients.clear();
	}

	public NavigationBodyState getState() {
		return state;
	}

	public void setState(NavigationBodyState state) {
		this.state = state;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public Collection<ProductViewDto> getProducts() {
		return products;
	}

	public void setProducts(Collection<ProductViewDto> products) {
		this.products = products;
	}

	public Collection<IngredientViewDto> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Collection<IngredientViewDto> ingredients) {
		this.ingredients = ingredients;
	}

	public ProductViewDto getProduct() {
		return product;
	}

	public void setProduct(ProductViewDto product) {
		this.product = product;
	}

	public IngredientViewDto getIngredient() {
		return ingredient;
	}

	public void setIngredient(IngredientViewDto ingredient) {
		this.ingredient = ingredient;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
