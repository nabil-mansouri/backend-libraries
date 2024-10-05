package com.nm.prices.soa.synchronizer;

import com.nm.prices.dtos.forms.old.PriceComputeBean;
import com.nm.prices.dtos.forms.old.PriceComputeCache;

/**
 * 
 * @author Nabil
 * 
 */
public interface PriceCacheBuilder {
	public PriceComputeCache build(PriceComputeBean reference);
}
