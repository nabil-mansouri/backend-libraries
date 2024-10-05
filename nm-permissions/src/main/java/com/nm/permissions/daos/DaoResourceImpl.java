package com.nm.permissions.daos;

import com.nm.permissions.models.Resource;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoResourceImpl extends AbstractGenericDao<Resource, String>implements DaoResource {
	@Override
	protected Class<Resource> getClassName() {
		return Resource.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "uuid";
	}

}
