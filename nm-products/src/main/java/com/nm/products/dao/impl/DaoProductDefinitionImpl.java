package com.nm.products.dao.impl;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.nm.products.dao.DaoProductDefinition;
import com.nm.products.model.ProductDefinition;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoProductDefinitionImpl extends AbstractGenericDao<ProductDefinition, Long>
		implements DaoProductDefinition {

	@Override
	protected Class<ProductDefinition> getClassName() {
		return ProductDefinition.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

	@Override
	@Transactional
	public void delete(ProductDefinition bean) {
		flush();
		// Delete Price
		super.delete(bean);
	}

	@Override
	@Transactional
	public void deleteAll(Collection<ProductDefinition> beans) {
		for (ProductDefinition d : beans) {
			this.delete(d);
		}
	}

}
