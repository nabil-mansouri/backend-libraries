package com.nm.prices.soa.computers;

import com.nm.prices.dtos.filters.PriceFilterDto;

/**
 * 
 * @author nabilmansouri
 *
 */
public interface PriceComputerFilterBuilder {
	public void reset(PriceFilterDto filter);

	public PriceFilterDto next(PriceFilterDto base);

	public PriceFilterDto create();

	public boolean hasNext();
}
