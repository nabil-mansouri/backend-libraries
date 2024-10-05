package com.nm.prices.soa.computers.impl;

import com.nm.prices.dtos.filters.PriceFilterDto;
import com.nm.prices.soa.computers.PriceComputerArguments;
import com.nm.prices.soa.computers.PriceComputerArgumentsIterator;
import com.nm.prices.soa.computers.PriceComputerFilterBuilder;

/***
 * 
 * @author nabilmansouri
 *
 */
final class LowestPriceComputerArgumentsIterator implements PriceComputerArgumentsIterator {
	private final LowestPriceComputerArguments arguments;
	private final PriceComputerFilterBuilder builder;
	private final PriceFilterDto base;

	public LowestPriceComputerArgumentsIterator(LowestPriceComputerArguments arguments,
			PriceComputerFilterBuilder builder) {
		this(arguments, builder, builder.create());
	}

	public LowestPriceComputerArgumentsIterator(LowestPriceComputerArguments arguments,
			PriceComputerFilterBuilder builder, PriceFilterDto base) {
		this.arguments = arguments;
		this.builder = builder;
		this.base = base;
	}

	public void reset() {
		builder.reset(base);
	}

	public PriceComputerArguments next() {
		PriceFilterDto filter = this.builder.next(base);
		arguments.setCurrent(filter);
		return arguments;
	}

	public boolean hasNext() {
		return builder.hasNext();
	}

}