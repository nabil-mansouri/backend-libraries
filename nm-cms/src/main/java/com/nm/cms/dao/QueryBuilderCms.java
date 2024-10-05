package com.nm.cms.dao;

import java.math.BigInteger;
import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.nm.cms.models.Cms;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderCms extends AbstractQueryBuilder<QueryBuilderCms> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QueryBuilderCms(Class<? extends Cms> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	private final DetachedCriteria criteria;

	public static QueryBuilderCms get() {
		return new QueryBuilderCms(Cms.class);
	}

	public QueryBuilderCms withAboutId(Collection<BigInteger> ids) {
		createAlias("subject", "subject");
		this.criteria.add(Restrictions.in("subject.id", ids));
		return this;
	}

	public QueryBuilderCms withContentIdProjection() {
		createAlias("data", "data");
		criteria.setProjection(Projections.property("data.id"));
		return this;
	}

	public QueryBuilderCms withActor(AbstractQueryBuilder<?> query) {
		createAlias("owner", "owner");
		this.criteria.add(Subqueries.propertyIn("owner.id", query.withNodeIdProjection().getQuery()));
		return this;
	}

	public QueryBuilderCms withSubject(AbstractQueryBuilder<?> query) {
		createAlias("subject", "subject");
		this.criteria.add(Subqueries.propertyIn("subject.id", query.withNodeIdProjection().getQuery()));
		return this;
	}

	public QueryBuilderCms withSubject(BigInteger query) {
		createAlias("subject", "subject");
		this.criteria.add(Restrictions.eq("subject.id", query));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
