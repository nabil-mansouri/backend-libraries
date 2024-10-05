package com.nm.shop.soa.checker;

import com.nm.shop.dtos.ShopStateDto;
import com.nm.shop.dtos.ShopViewDto;
import com.nm.shop.model.Shop;

/**
 * 
 * @author Nabil
 * 
 */
public interface ShopChecker {

	public void convert(Shop resto, ShopStateDto state);

	public void convert(ShopViewDto resto, ShopStateDto state);

}