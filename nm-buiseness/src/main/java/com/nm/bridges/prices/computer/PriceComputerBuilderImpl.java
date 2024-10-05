package com.nm.bridges.prices.computer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import com.nm.bridges.prices.OrderType;
import com.nm.bridges.prices.dtos.CustomPriceFilterDto;
import com.nm.prices.dtos.filters.PriceFilterDto;
import com.nm.prices.soa.computers.PriceComputerFilterBuilder;

/**
 * 
 * @author nabilmansouri
 *
 */
public class PriceComputerBuilderImpl implements PriceComputerFilterBuilder {
	private Collection<OrderType> types;
	private Iterator<OrderType> it;

	public void reset(PriceFilterDto filter) {
		if (filter instanceof CustomPriceFilterDto) {
			CustomPriceFilterDto c = (CustomPriceFilterDto) filter;
			if (c.getOrderType() == null) {
				types = Arrays.asList(OrderType.values());
			} else {
				types = Arrays.asList(c.getOrderType());
			}
			it = types.iterator();
		} else {
			throw new IllegalArgumentException("Filter is not of type CustomPriceFilterDto");
		}
	}

	public PriceFilterDto create() {
		return new CustomPriceFilterDto();
	}

	public PriceFilterDto next(PriceFilterDto base) {
		if (base instanceof CustomPriceFilterDto) {
			CustomPriceFilterDto clone = (CustomPriceFilterDto) base.clone();
			clone.setOrderType(it.next());
			return clone;
		} else {
			throw new IllegalArgumentException("Filter is not of type CustomPriceFilterDto");
		}
	}

	public boolean hasNext() {
		return it.hasNext();
	}
}
