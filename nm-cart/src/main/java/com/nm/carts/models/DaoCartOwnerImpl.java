package com.nm.carts.models;

import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoCartOwnerImpl extends AbstractGenericDao<CartOwner, Long>implements DaoCartOwner {

	@Override
	protected Class<CartOwner> getClassName() {
		return CartOwner.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

}
