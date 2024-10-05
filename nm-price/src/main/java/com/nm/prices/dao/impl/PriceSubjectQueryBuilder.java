package com.nm.prices.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.nm.prices.model.subject.PriceSubject;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class PriceSubjectQueryBuilder extends AbstractQueryBuilder<PriceSubjectQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected PriceSubjectQueryBuilder(Class<? extends PriceSubject> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	protected final DetachedCriteria criteria;

	public PriceSubjectQueryBuilder withRoot(boolean root) {
		this.criteria.add(Restrictions.eq("root", root));
		return this;
	}

	public PriceSubjectQueryBuilder withPrice(PriceQueryBuilder query) {
		this.criteria.add(Subqueries.propertyIn("id", query.withSubjectProjection().getQuery()));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
