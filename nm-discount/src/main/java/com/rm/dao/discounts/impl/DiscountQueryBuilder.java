package com.rm.dao.discounts.impl;

import java.util.Collection;
import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.rm.dao.discounts.lifecycle.DiscountLifeCycleQueryBuilder;
import com.rm.model.discounts.DiscountDefinition;
import com.rm.utils.dao.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class DiscountQueryBuilder extends AbstractQueryBuilder<DiscountQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DetachedCriteria criteria;

	private DiscountQueryBuilder(DetachedCriteria c) {
		this.criteria = c;
	}

	public static DiscountQueryBuilder get() {
		return new DiscountQueryBuilder(DetachedCriteria.forClass(DiscountDefinition.class));
	}

	public DiscountQueryBuilder withId(Long id) {
		this.criteria.add(Restrictions.idEq(id));
		return this;
	}

	public DiscountQueryBuilder withIdNot(Collection<Long> id) {
		if (!id.isEmpty()) {
			this.criteria.add(Restrictions.not(Restrictions.in("id", id)));
		}
		return this;
	}

	public DiscountQueryBuilder withDisjunctionLifeCycle(AbstractQueryBuilder<?> query) {
		createAlias("lifecycle", "lifecycle");
		this.disjunction.add(Subqueries.propertyIn("lifecycle.id", query.getQuery()));
		return this;
	}

	public DiscountQueryBuilder withLifeCycle(AbstractQueryBuilder<?> query) {
		createAlias("lifecycle", "lifecycle");
		this.criteria.add(Subqueries.propertyIn("lifecycle.id", query.getQuery()));
		return this;
	}

	public DiscountQueryBuilder withLifeCycle(DiscountLifeCycleQueryBuilder query) {
		createAlias("lifecycle", "lifecycle");
		this.criteria.add(Subqueries.propertyIn("lifecycle.id", query.withIdProjection().getQuery()));
		return this;
	}

	public DiscountQueryBuilder withProjectionId() {
		this.criteria.setProjection(Projections.id());
		return this;
	}

	public DiscountQueryBuilder withCreatedAfter(Date created) {
		this.criteria.add(Restrictions.ge("created", created));
		return this;
	}

	public DiscountQueryBuilder withDate(Date date) {
		this.criteria.add(Restrictions.eq("date", date));
		return this;
	}

	public DiscountQueryBuilder withNumber(Double number) {
		this.criteria.add(Restrictions.eq("number", number));
		return this;
	}

	public DiscountQueryBuilder withDateAfter(Date date) {
		this.criteria.add(Restrictions.ge("date", date));
		return this;
	}

	public DiscountQueryBuilder withDateBefore(Date date) {
		this.criteria.add(Restrictions.le("date", date));
		return this;
	}

	public DiscountQueryBuilder withCreatedBefore(Date created) {
		this.criteria.add(Restrictions.le("created", created));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
