package com.nm.products.dao.impl;

import java.util.Collection;

import com.nm.products.dao.DaoCategory;
import com.nm.products.model.Category;
import com.nm.products.model.ProductDefinition;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class DaoCategoryImpl extends AbstractGenericDao<Category, Long>implements DaoCategory {

	@Override
	protected Class<Category> getClassName() {
		return Category.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

	@Override
	public void delete(Category bean) {
		flush();
		refresh(bean);
		// DELETE PRODUCTS
		for (ProductDefinition d : bean.getProductDefinitions()) {
			d.getCategories().remove(bean);
		}
		bean.getProductDefinitions().clear();
		// DELETE CHILDREN RELATION
		bean.getChildren().clear();
		// DELETE PARENT RELATION
		Collection<Category> parents = find(CategoryQueryBuilder.get().withChild(bean.getId()));
		for (Category c : parents) {
			c.getChildren().remove(bean);
		}
		flush();
		super.delete(bean);
	}
}
