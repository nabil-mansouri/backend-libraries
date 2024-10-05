package com.nm.prices.soa.converters.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.nm.prices.dtos.constants.PriceFilterEnum;
import com.nm.prices.dtos.constants.PriceFilterValueEnum;
import com.nm.utils.cartesians.CartesianArguments;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CriteriaFilterList {
	private ConcurrentMap<PriceFilterValueEnum, List<CriteriaFilter<PriceFilterValueEnum, ?>>> filtersValues = new ConcurrentHashMap<PriceFilterValueEnum, List<CriteriaFilter<PriceFilterValueEnum, ?>>>();
	private ConcurrentMap<PriceFilterEnum, List<CriteriaFilter<PriceFilterEnum, ?>>> filters = new ConcurrentHashMap<PriceFilterEnum, List<CriteriaFilter<PriceFilterEnum, ?>>>();

	public void push(CriteriaFilter<PriceFilterEnum, ?> cri) {
		this.filters.putIfAbsent(cri.getType(), new ArrayList<CriteriaFilter<PriceFilterEnum, ?>>());
		this.filters.get(cri.getType()).add(cri);
	}

	public void pushValue(CriteriaFilter<PriceFilterValueEnum, ?> cri) {
		this.filtersValues.putIfAbsent(cri.getType(), new ArrayList<CriteriaFilter<PriceFilterValueEnum, ?>>());
		this.filtersValues.get(cri.getType()).add(cri);
	}

	public CartesianArguments<CriteriaFilter<?, ?>> toCartesian() {
		CartesianArguments<CriteriaFilter<?, ?>> arg = new CartesianArguments<CriteriaFilter<?, ?>>();
		for (PriceFilterValueEnum f : filtersValues.keySet()) {
			List<CriteriaFilter<?, ?>> all = new ArrayList<CriteriaFilter<?, ?>>(filtersValues.get(f));
			arg.add(all);
		}
		for (PriceFilterEnum f : filters.keySet()) {
			List<CriteriaFilter<?, ?>> all = new ArrayList<CriteriaFilter<?, ?>>(filters.get(f));
			arg.add(all);
		}
		return arg;
	}
}
