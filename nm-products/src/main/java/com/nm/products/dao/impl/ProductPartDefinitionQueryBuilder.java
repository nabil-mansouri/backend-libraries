package com.nm.products.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.products.model.ProductDefinition;
import com.nm.products.model.ProductDefinitionPart;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class ProductPartDefinitionQueryBuilder extends AbstractQueryBuilder<ProductPartDefinitionQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ProductPartDefinitionQueryBuilder() {

	}

	private DetachedCriteria criteria = DetachedCriteria.forClass(ProductDefinitionPart.class);

	public static ProductPartDefinitionQueryBuilder get() {
		return new ProductPartDefinitionQueryBuilder();
	}

	public ProductPartDefinitionQueryBuilder withContainsProduct(ProductDefinition def) {
		this.criteria.createAlias("products", "products");
		this.criteria.add(Restrictions.eq("products.id", def.getId()));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
