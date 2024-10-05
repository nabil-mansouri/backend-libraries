package com.nm.prices.soa.computers;

import java.util.Collection;

import com.nm.prices.dtos.forms.PriceComputerResult;

/***
 * 
 * @author nabilmansouri
 *
 */
public interface PriceChooserStrategy {
	public PriceComputerResult choose(Collection<PriceComputerResult> results);
}
