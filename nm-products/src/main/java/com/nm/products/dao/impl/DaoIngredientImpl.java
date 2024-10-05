package com.nm.products.dao.impl;

import com.nm.products.dao.DaoIngredient;
import com.nm.products.model.IngredientDefinition;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class DaoIngredientImpl extends AbstractGenericDao<IngredientDefinition, Long>implements DaoIngredient {

	@Override
	protected Class<IngredientDefinition> getClassName() {
		return IngredientDefinition.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

}
