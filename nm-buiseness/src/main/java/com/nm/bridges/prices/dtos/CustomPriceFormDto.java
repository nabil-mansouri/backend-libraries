package com.nm.bridges.prices.dtos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.nm.prices.dtos.forms.PriceFormDto;
import com.nm.products.dtos.views.ProductViewDto;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonTypeName("CustomPriceFormDto")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomPriceFormDto extends PriceFormDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PriceFormNodeDto root = new PriceFormNodeDto();
	private Collection<PriceFormNodeDto> nodes = new ArrayList<PriceFormNodeDto>();
	private ProductViewDto product;
	private List<ProductViewDto> products = new ArrayList<ProductViewDto>();
	private boolean noProducts;
	private boolean noOrderType;

	public CustomPriceFormDto() {
		super(new CustomPriceFormFilterDto());
	}

	public ProductViewDto getProduct() {
		return product;
	}

	public void setProduct(ProductViewDto product) {
		this.product = product;
	}

	public PriceFormDto select(ProductViewDto select) {
		this.product = (select.clone().setSelected(true));
		return this;
	}

	public List<ProductViewDto> getProducts() {
		return products;
	}

	public void setProducts(List<ProductViewDto> products) {
		this.products = products;
	}

	public PriceFormNodeDto getRoot() {
		return root;
	}

	public boolean isNoOrderType() {
		return noOrderType;
	}

	public void setNoOrderType(boolean noOrderType) {
		this.noOrderType = noOrderType;
	}

	public boolean isNoProducts() {
		return noProducts;
	}

	public void setNoProducts(boolean noProducts) {
		this.noProducts = noProducts;
	}

	public void setRoot(PriceFormNodeDto root) {
		this.root = root;
	}

	public Collection<PriceFormNodeDto> getNodes() {
		return nodes;
	}

	public void setNodes(Collection<PriceFormNodeDto> nodes) {
		this.nodes = nodes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void compute() {
		this.setConfigError(this.isNoCurrency() || this.isNoOrderType() || this.isNoProducts());
	}
}
