package com.nm.prices.dao.impl;

import com.nm.prices.dao.DaoPrice;
import com.nm.prices.model.Price;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoPriceImpl extends AbstractGenericDao<Price, Long>implements DaoPrice {

	@Override
	protected Class<Price> getClassName() {
		return Price.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

}
