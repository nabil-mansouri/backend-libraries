package com.nm.shop.dao;

import java.util.List;

import com.nm.shop.constants.ShopConfigType;
import com.nm.shop.model.ShopConfiguration;
import com.nm.utils.hibernate.IGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public interface DaoShopConfiguration extends IGenericDao<ShopConfiguration, Long> {
	public List<ShopConfiguration> findByType(ShopConfigType type);
}
