package com.nm.app.history;

import org.hibernate.criterion.DetachedCriteria;

import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderHistoryActor extends AbstractQueryBuilder<QueryBuilderHistoryActor> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected QueryBuilderHistoryActor(Class<? extends HistoryActor> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	private final DetachedCriteria criteria;

	public static QueryBuilderHistoryActor get() {
		return new QueryBuilderHistoryActor(HistoryActor.class);
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
