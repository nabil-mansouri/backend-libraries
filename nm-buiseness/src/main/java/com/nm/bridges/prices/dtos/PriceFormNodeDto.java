package com.nm.bridges.prices.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.products.dtos.views.ProductNodeDto;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceFormNodeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductNodeDto node = new ProductNodeDto();
	private Collection<PriceFormNodeFilterDto> values = new ArrayList<PriceFormNodeFilterDto>();
	private boolean enable;

	public ProductNodeDto getNode() {
		return node;
	}

	public PriceFormNodeDto clear() {
		this.values.clear();
		return this;
	}

	public PriceFormNodeDto add(PriceFormNodeFilterDto n) {
		this.values.add(n);
		return this;
	}

	public PriceFormNodeDto setNode(ProductNodeDto node) {
		this.node = node;
		return this;
	}

	public boolean isEnable() {
		return enable;
	}

	public PriceFormNodeDto setEnable(boolean enable) {
		this.enable = enable;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Collection<PriceFormNodeFilterDto> getValues() {
		return values;
	}

	public PriceFormNodeDto setValues(Collection<PriceFormNodeFilterDto> values) {
		this.values = values;
		return this;
	}

}
