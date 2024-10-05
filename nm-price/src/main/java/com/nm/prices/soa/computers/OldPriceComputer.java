package com.nm.prices.soa.computers;

import com.nm.prices.dtos.forms.old.PriceComputeBean;
import com.nm.prices.model.Price;

/**
 * 
 * @author Nabil
 * 
 */
public interface OldPriceComputer {
	public static final String PriceComputer = "PriceComputer";
	public static final String DefaultStrategy = "PriceComputer.DefaultStrategy";
	public static final String FlexibleStrategy = "PriceComputer.FlexibleStrategy";
	public static final String AdditionnalStrategy = "PriceComputer.AdditionnalStrategy";

	public boolean compute(PriceComputeBean context, Price price);

	public boolean computeChildren(PriceComputeBean context, Price parent, Price child);

}
