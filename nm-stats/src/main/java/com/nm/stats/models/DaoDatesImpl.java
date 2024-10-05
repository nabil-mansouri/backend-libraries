package com.nm.stats.models;

import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoDatesImpl extends AbstractGenericDao<Dates, Long>implements DaoDates {

	@Override
	protected Class<Dates> getClassName() {
		return Dates.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

}
