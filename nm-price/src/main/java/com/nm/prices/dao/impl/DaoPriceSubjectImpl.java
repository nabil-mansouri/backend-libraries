package com.nm.prices.dao.impl;

import com.nm.prices.dao.DaoPriceSubject;
import com.nm.prices.model.subject.PriceSubject;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoPriceSubjectImpl extends AbstractGenericDao<PriceSubject, Long>implements DaoPriceSubject {

	@Override
	protected Class<PriceSubject> getClassName() {
		return PriceSubject.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

}
