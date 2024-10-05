package com.nm.plannings.dao.impl;

import java.math.BigInteger;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.nm.plannings.model.Planning;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class PlanningQueryBuilder extends AbstractQueryBuilder<PlanningQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DetachedCriteria criteria = DetachedCriteria.forClass(Planning.class);

	public DetachedCriteria getQuery() {
		return criteria;
	}

	public static PlanningQueryBuilder get() {
		return new PlanningQueryBuilder();
	}

	public PlanningQueryBuilder withAbout(BigInteger id) {
		criteria.createAlias("about", "about");
		criteria.add(Restrictions.eq("about.id", id));
		return this;
	}

	public PlanningQueryBuilder withAbout(AbstractQueryBuilder<?> query) {
		criteria.createAlias("about", "about");
		criteria.add(Subqueries.propertyIn("about.id", query.withNodeIdProjection().getQuery()));
		return this;
	}
}
