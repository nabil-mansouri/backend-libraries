package com.nm.products.dao.impl;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.nm.products.dtos.filters.IngredientFilterDto;
import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.ProductDefinition;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class IngredientQueryBuilder extends AbstractQueryBuilder<IngredientQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DetachedCriteria criteria = DetachedCriteria.forClass(IngredientDefinition.class);
	DetachedCriteria productCriteria = DetachedCriteria.forClass(ProductDefinition.class);

	public static IngredientQueryBuilder get() {
		return new IngredientQueryBuilder();
	}

	public IngredientQueryBuilder() {
		productCriteria.createAlias("availableIngredient", "ing");
		productCriteria.setProjection(Projections.property("ing.id"));
	}

	public IngredientQueryBuilder withProduct(Long idProductDef) {
		productCriteria.add(Restrictions.eq("id", idProductDef));
		criteria.add(Property.forName("id").in(productCriteria));
		return this;
	}

	public IngredientQueryBuilder withId(Long idProductDef) {
		criteria.add(Restrictions.idEq(idProductDef));
		return this;
	}

	public IngredientQueryBuilder withFilter(IngredientFilterDto filter) {
		if (filter.getId() != null) {
			this.withId(filter.getId());
		}
		withRange(filter);
		return this;
	}

	public IngredientQueryBuilder withProduct(ProductDefinition product) {
		return withProduct(product.getId());
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

	public IngredientQueryBuilder withIdNotIn(Collection<Long> notIds) {
		if (notIds != null && !notIds.isEmpty())
			criteria.add(Restrictions.not(Restrictions.in("id", notIds)));
		return this;
	}

}
