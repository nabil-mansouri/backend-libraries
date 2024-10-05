package com.nm.prices.dtos.forms.old;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Nabil
 * 
 */
public class PriceComputeCache {
	private Map<String, PriceComputeBean> prices = new HashMap<String, PriceComputeBean>();

	public Map<String, PriceComputeBean> getPrices() {
		return prices;
	}

	public void setPrices(Map<String, PriceComputeBean> prices) {
		this.prices = prices;
	}
}
