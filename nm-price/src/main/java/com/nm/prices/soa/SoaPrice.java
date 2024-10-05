package com.nm.prices.soa;

import java.util.Collection;

import com.nm.prices.contract.ContractPriceViewDto;
import com.nm.prices.dao.impl.PriceQueryBuilder;
import com.nm.prices.dao.impl.PriceValueQueryBuilder;
import com.nm.prices.dtos.constants.PriceSelector;
import com.nm.prices.dtos.filters.PriceFilterDto;
import com.nm.prices.dtos.forms.PriceFormDto;
import com.nm.prices.dtos.forms.PriceSelectorBean;
import com.nm.prices.model.Price;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public interface SoaPrice {
	public Collection<PriceSelectorBean> getPriceSelectors();

	public PriceSelector getPriceSelector() throws NoDataFoundException;

	public void setSelectedPriceSelectors(Collection<PriceSelector> form);

	public void setSelectedPriceSelectorBeans(Collection<PriceSelectorBean> form);
	public PriceFilterDto createFilter();

	public PriceFormDto createPrice() throws NoDataFoundException;

	public PriceFormDto editPrice(Long id) throws NoDataFoundException;

	public Collection<ContractPriceViewDto> fetch(PriceFilterDto filter, OptionsList options)
			throws NoDataFoundException;

	public Collection<ContractPriceViewDto> fetch(PriceQueryBuilder query, OptionsList options)
			throws NoDataFoundException;

	public Collection<ContractPriceViewDto> fetch(PriceValueQueryBuilder query, OptionsList options)
			throws NoDataFoundException;

	public Collection<ContractPriceViewDto> fetch(Long id, OptionsList options) throws NoDataFoundException;

	public PriceFormDto refresh(PriceFormDto form, OptionsList withOption) throws NoDataFoundException;

	public PriceFormDto saveOrUpdate(PriceFormDto bean, OptionsList options) throws NoDataFoundException;

	public Price removePrice(Long id);

}
