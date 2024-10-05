package com.nm.carts.models;

import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoCartImpl extends AbstractGenericDao<Cart, Long>implements DaoCart {

	@Override
	protected Class<Cart> getClassName() {
		return Cart.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

}
