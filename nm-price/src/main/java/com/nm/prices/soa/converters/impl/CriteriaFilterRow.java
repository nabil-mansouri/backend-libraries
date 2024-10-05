package com.nm.prices.soa.converters.impl;

import java.util.HashMap;
import java.util.Map;

import com.nm.prices.dtos.constants.PriceFilterEnum;
import com.nm.prices.dtos.constants.PriceFilterValueEnum;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CriteriaFilterRow {
	private Map<PriceFilterEnum, Object> filters = new HashMap<PriceFilterEnum, Object>();
	private Map<PriceFilterValueEnum, Object> valuesFilters = new HashMap<PriceFilterValueEnum, Object>();

	public boolean is(PriceFilterValueEnum en) {
		return false;
	}

	public boolean is(PriceFilterEnum en) {
		return false;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(PriceFilterEnum en, Class<T> clazz) {
		return (T) filters.get(en);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(PriceFilterValueEnum en, Class<T> clazz) {
		return (T) valuesFilters.get(en);

	}

	public void put(PriceFilterEnum en, Object o) {
		filters.put(en, o);
	}

	public void put(PriceFilterValueEnum en, Object o) {
		valuesFilters.put(en, o);
	}
}
