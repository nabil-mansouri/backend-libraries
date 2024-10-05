package com.rm.dao.clients.impl;

import org.springframework.stereotype.Repository;

import com.rm.dao.clients.DaoClientCriterias;
import com.rm.model.clients.criterias.ClientCriterias;
import com.rm.utils.dao.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
@Repository
public class DaoClientCriteriasImpl extends AbstractGenericDao<ClientCriterias, Long> implements DaoClientCriterias {

	@Override
	protected Class<ClientCriterias> getClassName() {
		return ClientCriterias.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

}
