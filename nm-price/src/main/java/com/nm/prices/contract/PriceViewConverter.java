package com.nm.prices.contract;

import java.util.Collection;

import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public interface PriceViewConverter {

	public Collection<ContractPriceViewDto> toDto(Collection<ContractPriceFilterViewModel> view, OptionsList options)
			throws NoDataFoundException;

	public ContractPriceViewDto toDto(ContractPriceFilterViewModel value, OptionsList options) throws NoDataFoundException;

}