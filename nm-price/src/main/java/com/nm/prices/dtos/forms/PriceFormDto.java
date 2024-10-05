package com.nm.prices.dtos.forms;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.nm.app.currency.CurrencyDto;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class PriceFormDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private PriceFormFilterDto filter;
	private boolean noCurrency;
	private boolean configError;
	private CurrencyDto currency = new CurrencyDto();

	public PriceFormDto(PriceFormFilterDto filter) {
		setFilter(filter);
	}

	public CurrencyDto getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyDto currency) {
		this.currency = currency;
	}

	public boolean isConfigError() {
		return configError;
	}

	public void setConfigError(boolean configError) {
		this.configError = configError;
	}

	public void compute() {
		this.setConfigError(this.isNoCurrency());
	}

	public boolean isNoCurrency() {
		return noCurrency;
	}

	public void setNoCurrency(boolean noCurrency) {
		this.noCurrency = noCurrency;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PriceFormFilterDto getFilter() {
		return filter;
	}

	public void setFilter(PriceFormFilterDto filter) {
		this.filter = filter;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
