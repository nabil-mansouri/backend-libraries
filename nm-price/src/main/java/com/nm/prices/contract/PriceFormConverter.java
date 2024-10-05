package com.nm.prices.contract;

import com.nm.prices.dtos.forms.PriceFormDto;
import com.nm.prices.model.Price;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public interface PriceFormConverter {

	public PriceFormDto toDto(Price price, OptionsList options) throws NoDataFoundException;

	public PriceFormDto toDto(PriceFormDto price, OptionsList options) throws NoDataFoundException;

	public Price toEntity(PriceFormDto bean, OptionsList options) throws NoDataFoundException;

}