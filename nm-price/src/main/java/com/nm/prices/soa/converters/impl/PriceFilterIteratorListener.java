package com.nm.prices.soa.converters.impl;

import com.nm.prices.model.values.PriceValue;

/**
 * 
 * @author nabilmansouri
 *
 */
public interface PriceFilterIteratorListener {
	public void founded(PriceValue value, CriteriaFilterRow criterias);
}
