package com.nm.shop.dao.impl;

import com.nm.shop.dao.DaoShop;
import com.nm.shop.model.Shop;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoShopImpl extends AbstractGenericDao<Shop, Long>implements DaoShop {

	@Override
	protected Class<Shop> getClassName() {
		return Shop.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

}
