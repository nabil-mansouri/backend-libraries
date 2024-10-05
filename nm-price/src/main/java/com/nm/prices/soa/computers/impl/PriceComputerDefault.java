package com.nm.prices.soa.computers.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.nm.prices.dtos.constants.PriceComputeError;
import com.nm.prices.dtos.constants.PriceOperationType;
import com.nm.prices.dtos.forms.PriceComputerResult;
import com.nm.prices.dtos.forms.PriceComputerResultNode;
import com.nm.prices.dtos.forms.PriceSubjectBean;
import com.nm.prices.model.Price;
import com.nm.prices.model.PriceComposed;
import com.nm.prices.model.values.PriceValue;
import com.nm.prices.model.values.PriceValueSimple;
import com.nm.prices.soa.computers.PriceChooserStrategy;
import com.nm.prices.soa.computers.PriceComputer;
import com.nm.prices.soa.computers.PriceComputerArguments;
import com.nm.prices.soa.computers.PriceComputerArgumentsIterator;

/**
 * 
 * @author nabilmansouri
 *
 */
public class PriceComputerDefault implements PriceComputer {

	public PriceComputerResult compute(PriceComputerArguments binder, PriceChooserStrategy strategy,
			PriceComputerResult result) {
		Collection<Price> prices = binder.prices();
		if (prices.isEmpty()) {
			result.getErrors().add(PriceComputeError.NoPriceFounded);
			return result;
		} else {
			Collection<PriceComputerResult> allResults = new ArrayList<PriceComputerResult>();
			for (Price p : prices) {
				PriceComputerResult clone = result.clone();
				compute(p, binder, clone);
				allResults.add(clone);
			}
			return strategy.choose(allResults);
		}
	}

	protected void compute(Price price, PriceComputerArguments binder, PriceComputerResult result) {
		if (binder.has(price.getSubject())) {
			Collection<PriceValue> values = binder.values(price);
			for (PriceValue v : values) {
				PriceValueSimple s = (PriceValueSimple) v;
				result.getNodes().add(new PriceComputerResultNode(PriceOperationType.Add, s.getValue(), v.getId(),
						new PriceSubjectBean(price.getSubject().getId())));
			}
		}
		//
		if (price instanceof PriceComposed) {
			for (Price p : ((PriceComposed) price).getChildren()) {
				compute(p, binder, result);
			}
		}
	}

	public PriceComputerResult compute(PriceComputerArgumentsIterator iterator, PriceChooserStrategy strategy,
			PriceComputerResult result) {
		iterator.reset();
		Collection<PriceComputerResult> allResults = new ArrayList<PriceComputerResult>();
		while (iterator.hasNext()) {
			allResults.add(compute(iterator.next(), strategy, result));
		}
		return strategy.choose(allResults);
	}
}
