package com.rm.soa.discounts.converters;

import com.rm.soa.discounts.beans.DiscountCacheBean;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public interface DiscountCache {

	public PriceForm getPrice(DiscountCacheBean cache, Long id, String lang) throws NoDataFoundException;

}