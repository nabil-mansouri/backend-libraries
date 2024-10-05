package com.nm.prices.dtos.forms.old;

/**
 * 
 * @author Nabil
 * 
 */
public class PriceSynchronizerContext {
//	private Collection<ProductViewDto> changed = new ArrayList<ProductViewDto>();
	private PriceComputeCache cache = new PriceComputeCache();
	private Double total = 0d;

	public PriceSynchronizerContext() {
	}

	public PriceSynchronizerContext(PriceComputeCache cache) {
		setCache(cache);
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public void addTotal(Double value) {
		if (value != null) {
			this.total += value;
		}
	}

	public PriceComputeCache getCache() {
		return cache;
	}

	public void setCache(PriceComputeCache cache) {
		this.cache = cache;
	}

	// public Collection<ProductViewDto> getChanged() {
	// return changed;
	// }
	//
	// public void setChanged(Collection<ProductViewDto> changed) {
	// this.changed = changed;
	// }

	public boolean existsPrice(String path) {
		return this.cache.getPrices().containsKey(path);
	}

	public PriceComputeBean getPrice(String path) {
		return this.cache.getPrices().get(path);
	}
}
