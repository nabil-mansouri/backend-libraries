package com.nm.bridges.prices.dtos;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.app.currency.CurrencyDto;
import com.nm.prices.contract.ContractPriceViewDto;
import com.nm.products.dtos.views.ProductViewDto;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceViewDto implements Serializable, ContractPriceViewDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Double value;
	private ProductViewDto product = new ProductViewDto();
	private PriceViewFilterDto filter = new PriceViewFilterDto();
	private Long countSupplements;
	private CurrencyDto currency = new CurrencyDto();

	public CurrencyDto getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyDto currency) {
		this.currency = currency;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductViewDto getProduct() {
		return product;
	}

	public void setProduct(ProductViewDto product) {
		this.product = product;
	}

	public PriceViewFilterDto getFilter() {
		return filter;
	}

	public void setFilter(PriceViewFilterDto filter) {
		this.filter = filter;
	}

	public Long getCountSupplements() {
		return countSupplements;
	}

	public void setCountSupplements(Long countSupplements) {
		this.countSupplements = countSupplements;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public PriceViewDto clone() {
		return SerializationUtils.clone(this);
	}
}
