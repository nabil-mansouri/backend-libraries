package com.nm.prices.soa.computers.impl;

import java.util.Collection;

import com.nm.prices.dtos.forms.PriceComputerResult;
import com.nm.prices.soa.computers.PriceChooserStrategy;

/**
 * 
 * @author nabilmansouri
 *
 */
public class PriceChooserStrategyMaximum implements PriceChooserStrategy {

	public PriceComputerResult choose(Collection<PriceComputerResult> results) {
		PriceComputerResult chosen = null;
		for (PriceComputerResult c : results) {
			if (chosen == null) {
				chosen = c;
			}
			if (chosen.total() < c.total()) {
				chosen = c;
			}
		}
		return chosen;
	}

}
