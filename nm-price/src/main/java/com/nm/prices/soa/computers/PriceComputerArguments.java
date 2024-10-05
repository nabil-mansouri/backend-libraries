package com.nm.prices.soa.computers;

import java.util.Collection;

import com.nm.prices.model.Price;
import com.nm.prices.model.subject.PriceSubject;
import com.nm.prices.model.values.PriceValue;

/***
 * 
 * @author nabilmansouri
 *
 */
public interface PriceComputerArguments {
	public Collection<Price> prices();

	public boolean has(PriceSubject subject);

	public Collection<PriceValue> values(Price price);

}
