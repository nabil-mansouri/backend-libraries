package com.nm.products.dtos.old;

import com.nm.products.dtos.views.ProductPartViewDto;
import com.nm.products.dtos.views.ProductViewDto;

/**
 * 
 * @author Nabil
 * 
 */
public class ProductCheckerContextProductSelection {
	private ProductPartViewDto part;
	private ProductViewDto product;

	public ProductCheckerContextProductSelection() {
	}

	public ProductCheckerContextProductSelection(ProductPartViewDto part, ProductViewDto product) {
		super();
		this.part = part;
		this.product = product;
	}

	public ProductPartViewDto getPart() {
		return part;
	}

	public void setPart(ProductPartViewDto part) {
		this.part = part;
	}

	public ProductViewDto getProduct() {
		return product;
	}

	public void setProduct(ProductViewDto product) {
		this.product = product;
	}

}
