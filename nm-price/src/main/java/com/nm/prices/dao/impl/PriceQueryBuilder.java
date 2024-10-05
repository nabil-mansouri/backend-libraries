package com.nm.prices.dao.impl;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;

import com.nm.prices.dtos.constants.PriceFilterEnum;
import com.nm.prices.dtos.constants.PriceFilterEnum.PriceFilterEnumDefault;
import com.nm.prices.dtos.filters.PriceFilterDto;
import com.nm.prices.model.Price;
import com.nm.prices.model.filter.PriceFilter;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class PriceQueryBuilder extends AbstractQueryBuilder<PriceQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final DetachedCriteria criteria;

	protected PriceQueryBuilder(Class<? extends Price> clazz) {
		this(clazz, "Price");
	}

	protected PriceQueryBuilder(Class<? extends Price> clazz, String alias) {
		criteria = DetachedCriteria.forClass(clazz, alias);
		withMainAlias(alias);
	}

	public String getFilterIdName() {
		createAlias(wrapMain("filter"), "filter", JoinType.LEFT_OUTER_JOIN);
		return "filter.id";
	}

	public String getIdName() {
		return wrapMain("id");
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

	public PriceQueryBuilder withFilter(PriceFilterDto request) {
		if (request.isOnlyCurrent()) {
			withOnlyCurrent();
		}
		withRange(request);
		return this;
	}

	public PriceQueryBuilder withValue(PriceValueQueryBuilder query) {
		createAlias("values", "values");
		this.criteria.add(Subqueries.propertyIn("values.id", query.withIdProjection().getQuery()));
		return this;
	}

	public PriceQueryBuilder withFilterOrNotExists(PriceFilterQueryBuilder query, PriceFilterEnum type) {
		Disjunction or = Restrictions.or();
		or.add(Subqueries.propertyIn(wrapMain("id"), query.clone().withPriceProjection().withType(type).getQuery()));
		or.add(Subqueries.notExists(new PriceFilterQueryBuilder(PriceFilter.class, "subfilter").withJoin(this)
				.withType(type).withIdProjection().getQuery()));
		this.criteria.add(or);
		return this;
	}

	public PriceQueryBuilder withOnlyCurrent() {
		return this.withOnlyCurrent(true);
	}

	public PriceQueryBuilder withOnlyCurrent(Boolean current) {
		Date now = new Date();
		//
		PriceQueryBuilder q0 = new PriceQueryBuilder(Price.class)
				.withFilterOrNotExists(PriceFilterQueryBuilder.getDate().withDateLessTE(now),
						PriceFilterEnumDefault.LimitInTimeFrom)
				.withFilterOrNotExists(PriceFilterQueryBuilder.getDate().withDateGreaterTE(now),
						PriceFilterEnumDefault.LimitInTimeTo);
		if (current) {
			criteria.add(Subqueries.propertyIn("id", q0.withIdProjection().getQuery()));
		} else {
			criteria.add(Subqueries.propertyNotIn("id", q0.withIdProjection().getQuery()));
		}
		return this;
	}

	public PriceQueryBuilder withOrderByLast() {
		criteria.addOrder(Order.desc(wrapMain("created")));
		return this;
	}

	public PriceQueryBuilder withSubject(PriceSubjectQueryBuilder query) {
		createAlias(wrapMain("subject"), "subject");
		this.criteria.add(Subqueries.propertyIn("subject.id", query.withIdProjection().getQuery()));
		return this;
	}

	public PriceQueryBuilder withSubjectProjection() {
		createAlias(wrapMain("subject"), "subject");
		this.criteria.setProjection(Projections.property("subject.id"));
		return this;
	}
}
