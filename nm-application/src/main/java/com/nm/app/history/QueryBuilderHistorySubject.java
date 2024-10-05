package com.nm.app.history;

import org.hibernate.criterion.DetachedCriteria;

import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderHistorySubject extends AbstractQueryBuilder<QueryBuilderHistorySubject> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected QueryBuilderHistorySubject(Class<? extends HistorySubject> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	private final DetachedCriteria criteria;

	public static QueryBuilderHistorySubject get() {
		return new QueryBuilderHistorySubject(HistorySubject.class);
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
