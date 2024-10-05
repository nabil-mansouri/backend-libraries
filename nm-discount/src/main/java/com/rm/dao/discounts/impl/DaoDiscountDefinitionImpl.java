package com.rm.dao.discounts.impl;

import org.springframework.stereotype.Repository;

import com.rm.dao.discounts.DaoDiscount;
import com.rm.model.discounts.DiscountDefinition;
import com.rm.utils.dao.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
@Repository
public class DaoDiscountDefinitionImpl extends AbstractGenericDao<DiscountDefinition, Long> implements DaoDiscount {

	@Override
	protected Class<DiscountDefinition> getClassName() {
		return DiscountDefinition.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

}
