package com.nm.products.dtos.views;

import java.util.ArrayList;
import java.util.Collection;

import com.nm.products.constants.ProductNodeType;
import com.nm.utils.graphs.finder.IGraphIdentifier;

/**
 * 
 * @author nabilmansouri
 *
 */
public class ProductAsTreeIdentifier implements IGraphIdentifier {
	private Collection<ProductNodeType> types = new ArrayList<ProductNodeType>();
	private ProductViewDto product;

	public ProductAsTreeIdentifier(ProductViewDto product) {
		super();
		this.product = product;
	}

	public ProductViewDto getProduct() {
		return product;
	}

	public void setProduct(ProductViewDto product) {
		this.product = product;
	}

	public Collection<ProductNodeType> getTypes() {
		return types;
	}

	public void setTypes(Collection<ProductNodeType> types) {
		this.types = types;
	}

	public ProductAsTreeIdentifier add(ProductNodeType e) {
		types.add(e);
		return this;
	}
}
