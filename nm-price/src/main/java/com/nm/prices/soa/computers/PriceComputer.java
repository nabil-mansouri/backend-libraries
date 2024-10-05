package com.nm.prices.soa.computers;

import com.nm.prices.dtos.forms.PriceComputerResult;

/**
 * 
 * @author nabilmansouri
 *
 */
public interface PriceComputer {
	public PriceComputerResult compute(PriceComputerArguments arguments, PriceChooserStrategy strategy,
			PriceComputerResult result);

	public PriceComputerResult compute(PriceComputerArgumentsIterator iterator, PriceChooserStrategy strategy,
			PriceComputerResult result);
}
