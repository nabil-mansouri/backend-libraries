package com.nm.prices.dao.impl;

import com.nm.prices.dao.DaoPriceValue;
import com.nm.prices.model.values.PriceValue;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoPriceValueImpl extends AbstractGenericDao<PriceValue, Long>implements DaoPriceValue {

	@Override
	protected Class<PriceValue> getClassName() {
		return PriceValue.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

}
