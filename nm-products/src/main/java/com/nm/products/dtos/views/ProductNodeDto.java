package com.nm.products.dtos.views;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.nm.products.constants.ProductNodeType;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class ProductNodeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private ProductNodeType type;
	private ProductViewDto product;
	private ProductPartViewDto part;
	private IngredientViewDto ingredient;

	public ProductNodeType getType() {
		return type;
	}

	public ProductNodeDto setType(ProductNodeType type) {
		this.type = type;
		return this;
	}

	public ProductViewDto getProduct() {
		return product;
	}

	public ProductNodeDto setProduct(ProductViewDto product) {
		this.product = product;
		return this;
	}

	public ProductPartViewDto getPart() {
		return part;
	}

	public void setPart(ProductPartViewDto part) {
		this.part = part;
	}

	public IngredientViewDto getIngredient() {
		return ingredient;
	}

	public void setIngredient(IngredientViewDto ingredient) {
		this.ingredient = ingredient;
	}

	@Override
	public String toString() {
		return "ProductAsListNodeBean [type=" + type + ", product=" + product + ", part=" + part + ", ingredient="
				+ ingredient + "]";
	}

}
