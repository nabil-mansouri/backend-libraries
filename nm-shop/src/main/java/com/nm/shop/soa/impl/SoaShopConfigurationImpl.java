package com.nm.shop.soa.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.nm.shop.constants.ShopConfigType;
import com.nm.shop.dao.DaoShopConfiguration;
import com.nm.shop.model.ShopConfiguration;

/**
 * 
 * @author Nabil
 * 
 */
public class SoaShopConfigurationImpl implements SoaShopConfiguration {
	private DaoShopConfiguration daoShopConfiguration;

	public void setDaoShopConfiguration(DaoShopConfiguration daoShopConfiguration) {
		this.daoShopConfiguration = daoShopConfiguration;
	}

	private ShopConfiguration get(ShopConfigType type) {
		List<ShopConfiguration> l = daoShopConfiguration.findByType(type);
		if (l.isEmpty()) {
			return null;
		} else {
			return l.iterator().next();
		}
	}

	private ShopConfiguration getOrCreate(ShopConfigType type) {
		ShopConfiguration config = get(type);
		if (config == null) {
			config = new ShopConfiguration();
			config.setType(type);
		}
		return config;
	}

	@Transactional(readOnly = true)
	public boolean getBoolean(ShopConfigType type, boolean defaul) {
		ShopConfiguration config = get(type);
		if (config == null) {
			return defaul;
		} else {
			return Boolean.valueOf(config.getValue());
		}
	}

	@Transactional()
	public ShopConfiguration setBoolean(ShopConfigType type, boolean value) {
		ShopConfiguration config = getOrCreate(type);
		config.setValue(Boolean.toString(value));
		daoShopConfiguration.saveOrUpdate(config);
		return config;
	}

	@Transactional(readOnly = true)
	public int getInt(ShopConfigType type, int defaul) {
		ShopConfiguration config = get(type);
		if (config == null) {
			return defaul;
		} else {
			return Integer.valueOf(config.getValue());
		}
	}

	@Transactional()
	public ShopConfiguration setInt(ShopConfigType type, int value) {
		ShopConfiguration config = getOrCreate(type);
		config.setValue(Integer.toString(value));
		daoShopConfiguration.saveOrUpdate(config);
		return config;
	}

}
