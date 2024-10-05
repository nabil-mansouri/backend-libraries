package com.nm.prices.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;

import com.nm.prices.dtos.constants.PriceFilterValueEnum;
import com.nm.prices.dtos.filters.PriceFilterDto;
import com.nm.prices.model.Price;
import com.nm.prices.model.values.PriceValue;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class PriceValueQueryBuilder extends AbstractQueryBuilder<PriceValueQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DetachedCriteria criteria = DetachedCriteria.forClass(PriceValue.class);

	protected PriceValueQueryBuilder() {

	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

	public PriceValueQueryBuilder withFilter(PriceFilterDto request) {
		return this;
	}

	public PriceValueQueryBuilder withPrice(Price price) {
		createAlias("price", "price");
		this.criteria.add(Restrictions.eq("price", price));
		return this;
	}

	public PriceValueQueryBuilder withFilterOrNotExists(PriceValueFilterQueryBuilder query, PriceFilterValueEnum type) {
		this.createAlias("filter", "filter", JoinType.LEFT_OUTER_JOIN);
		Disjunction or = Restrictions.or();
		or.add(Subqueries.propertyIn("filter.id", query.withIdProjection().getQuery()));
		or.add(Subqueries.notExists(
				PriceValueFilterQueryBuilder.get().withJoin(this).withType(type).withIdProjection().getQuery()));
		this.criteria.add(or);
		return this;
	}

	public String getFilterIdName() {
		createAlias("filter", "filter", JoinType.LEFT_OUTER_JOIN);
		return "filter.id";
	}

}
