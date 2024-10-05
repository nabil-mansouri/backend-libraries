package com.nm.datas.daos;

import org.hibernate.criterion.DetachedCriteria;

import com.nm.datas.models.AppData;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderAppData extends AbstractQueryBuilder<QueryBuilderAppData> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final DetachedCriteria criteria;

	public static QueryBuilderAppData get() {
		return new QueryBuilderAppData(AppData.class);
	}

	protected QueryBuilderAppData(Class<? extends AppData> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
