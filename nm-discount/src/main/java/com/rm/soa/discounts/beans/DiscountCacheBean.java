package com.rm.soa.discounts.beans;

import java.util.HashMap;
import java.util.Map;

/***
 * 
 * @author Nabil
 * 
 */
public class DiscountCacheBean {
	Map<Long, PriceForm> priceByProduct = new HashMap<Long, PriceForm>();

	public Map<Long, PriceForm> getPriceByProduct() {
		return priceByProduct;
	}

	public void setPriceByProduct(Map<Long, PriceForm> priceByProduct) {
		this.priceByProduct = priceByProduct;
	}

}
