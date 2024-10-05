package com.nm.prices.dao.impl;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.nm.prices.dtos.constants.PriceFilterEnum;
import com.nm.prices.model.filter.PriceFilter;
import com.nm.prices.model.filter.PriceFilterDate;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class PriceFilterQueryBuilder extends AbstractQueryBuilder<PriceFilterQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected PriceFilterQueryBuilder(Class<? extends PriceFilter> clazz) {
		this(clazz, "PriceFilter");
	}

	protected PriceFilterQueryBuilder(Class<? extends PriceFilter> clazz, String alias) {
		criteria = DetachedCriteria.forClass(clazz, alias);
		withMainAlias(alias);
	}

	protected final DetachedCriteria criteria;

	public static PriceFilterQueryBuilder getDate() {
		return new PriceFilterQueryBuilder(PriceFilterDate.class);
	}

	public static PriceFilterQueryBuilder getDate(String alias) {
		return new PriceFilterQueryBuilder(PriceFilterDate.class, alias);
	}

	public PriceFilterQueryBuilder withDateGreaterT(Date d) {
		criteria.add(Restrictions.gt(wrapMain("date"), d));
		return this;
	}

	public PriceFilterQueryBuilder withType(PriceFilterEnum d) {
		criteria.add(Restrictions.eq(wrapMain("type"), d));
		return this;
	}

	public PriceFilterQueryBuilder withDateGreaterTE(Date d) {
		criteria.add(Restrictions.ge(wrapMain("date"), d));
		return this;
	}

	public PriceFilterQueryBuilder withDateLessT(Date d) {
		criteria.add(Restrictions.lt(wrapMain("date"), d));
		return this;
	}

	public PriceFilterQueryBuilder withDateLessTE(Date d) {
		criteria.add(Restrictions.le(wrapMain("date"), d));
		return this;
	}

	public PriceFilterQueryBuilder withPriceProjection() {
		createAlias(wrapMain("price"), "price");
		this.criteria.setProjection(Projections.property("price.id"));
		return this;
	}

	public PriceFilterQueryBuilder withJoin(PriceQueryBuilder query) {
		createAlias(wrapMain("price"), "price");
		criteria.add(Property.forName("price.id").eqProperty(query.getIdName()));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
