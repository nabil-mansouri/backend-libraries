package com.nm.prices.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.nm.prices.dtos.constants.PriceFilterValueEnum;
import com.nm.prices.model.filter.PriceValueFilter;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class PriceValueFilterQueryBuilder extends AbstractQueryBuilder<PriceValueFilterQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected PriceValueFilterQueryBuilder(Class<? extends PriceValueFilter> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	protected final DetachedCriteria criteria;

	protected static PriceValueFilterQueryBuilder get() {
		return new PriceValueFilterQueryBuilder(PriceValueFilter.class);
	}

	public PriceValueFilterQueryBuilder withJoin(PriceValueQueryBuilder query) {
		criteria.add(Property.forName("id").eqProperty(query.getFilterIdName()));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

	public PriceValueFilterQueryBuilder withType(PriceFilterValueEnum type) {
		criteria.add(Restrictions.eq("type", type));
		return this;
	}
}
