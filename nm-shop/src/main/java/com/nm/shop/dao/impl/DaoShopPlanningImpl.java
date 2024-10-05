package com.nm.shop.dao.impl;

import com.nm.shop.bridge.ShopPlanningable;
import com.nm.shop.dao.DaoShopPlanningable;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoShopPlanningImpl extends AbstractGenericDao<ShopPlanningable, Long>implements DaoShopPlanningable {

	@Override
	protected Class<ShopPlanningable> getClassName() {
		return ShopPlanningable.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

}
