package com.nm.prices.contract;

import java.util.Collection;

import com.nm.prices.dao.impl.PriceQueryBuilder;
import com.nm.prices.dao.impl.PriceSubjectQueryBuilder;
import com.nm.prices.dao.impl.PriceValueQueryBuilder;
import com.nm.prices.dtos.filters.PriceFilterDto;
import com.nm.prices.soa.computers.PriceComputerFilterBuilder;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author nabilmansouri
 *
 */
public interface PriceAdapterContract {
	public OptionsList getAllOptions();

	public PriceFormConverter getFormConverter();

	public PriceViewConverter getViewConverter();

	public PriceQueryBuilder buildQuery();

	public PriceSubjectQueryBuilder buildSubjectQuery();

	public PriceComputerFilterBuilder buildComputer();

	public PriceValueQueryBuilder buildValueQuery();

	public PriceFilterDto createFilter();

	public Collection<ContractPriceFilterViewModel> fetch(PriceQueryBuilder query);

	public Collection<ContractPriceFilterViewModel> fetch(PriceValueQueryBuilder query);

	public Collection<ContractPriceFilterViewModel> fetch(PriceFilterDto query);

	public Collection<ContractPriceFilterViewModel> fetch(Long id);
}
