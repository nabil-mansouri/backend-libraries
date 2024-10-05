package com.nm.prices.soa.synchronizer.impl;

import com.nm.prices.dtos.forms.old.PriceComputeBean;
import com.nm.prices.dtos.forms.old.PriceComputeCache;
import com.nm.prices.soa.synchronizer.PriceCacheBuilder;
import com.nm.utils.graphs.AbstractGraph;
import com.nm.utils.graphs.iterators.GraphIteratorBuilder;
import com.nm.utils.graphs.listeners.DefaultIteratorListener;

/**
 * 
 * @author Nabil
 * 
 */
public class PriceCacheBuilderImpl implements PriceCacheBuilder {

	public PriceComputeCache build(PriceComputeBean reference) {
		final PriceComputeCache cache = new PriceComputeCache();
		GraphIteratorBuilder.buildDeep().iterate(reference, new DefaultIteratorListener() {

			public boolean onFounded(AbstractGraph node) {
				PriceComputeBean nodePrice = (PriceComputeBean) node;
				cache.getPrices().put(nodePrice.getPath(), nodePrice);
				return true;
			}

		});
		return cache;
	}

}
