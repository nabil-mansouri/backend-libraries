package com.nm.tests.bridges;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nm.prices.contract.ContractPriceFilterViewModel;
import com.nm.prices.contract.ContractPriceViewDto;
import com.nm.prices.contract.PriceFormConverter;
import com.nm.prices.contract.PriceViewConverter;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public class PriceViewConverterImpl implements PriceViewConverter {
	protected static Log log = LogFactory.getLog(PriceFormConverter.class);

	public Collection<ContractPriceViewDto> toDto(Collection<ContractPriceFilterViewModel> view, OptionsList options)
			throws NoDataFoundException {
		return new ArrayList<ContractPriceViewDto>();
	}

	public ContractPriceViewDto toDto(ContractPriceFilterViewModel view, OptionsList options)
			throws NoDataFoundException {
		return new ContractPriceViewDto() {
		};
	}

}
