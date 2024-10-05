package com.nm.app.history;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Subqueries;

import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderHistory extends AbstractQueryBuilder<QueryBuilderHistory> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QueryBuilderHistory(Class<? extends History> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	private final DetachedCriteria criteria;

	public static QueryBuilderHistory get() {
		return new QueryBuilderHistory(History.class);
	}

	public QueryBuilderHistory withActor(QueryBuilderHistoryActor query) {
		createAlias("actor", "actor");
		this.criteria.add(Subqueries.propertyIn("actor.id", query.withIdProjection().getQuery()));
		return this;
	}

	public QueryBuilderHistory withSubject(QueryBuilderHistorySubject query) {
		createAlias("subject", "subject");
		this.criteria.add(Subqueries.propertyIn("subject.id", query.withIdProjection().getQuery()));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
