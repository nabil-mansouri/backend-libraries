package com.nm.products.dao.impl;

import com.nm.products.dao.DaoProductDefinitionPart;
import com.nm.products.model.ProductDefinitionPart;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoProductDefinitionPartImpl extends AbstractGenericDao<ProductDefinitionPart, Long>
		implements DaoProductDefinitionPart {
	@Override
	protected Class<ProductDefinitionPart> getClassName() {
		return ProductDefinitionPart.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

}
