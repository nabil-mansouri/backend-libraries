package com.nm.shop.soa.impl;

import com.nm.shop.constants.ShopConfigType;
import com.nm.shop.model.ShopConfiguration;

/**
 * 
 * @author Nabil
 * 
 */
public interface SoaShopConfiguration {

	public boolean getBoolean(ShopConfigType type, boolean defaul);

	public ShopConfiguration setBoolean(ShopConfigType type, boolean value);

	public int getInt(ShopConfigType type, int defaul);

	public ShopConfiguration setInt(ShopConfigType type, int value);
}
