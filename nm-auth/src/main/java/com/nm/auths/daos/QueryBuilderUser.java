package com.nm.auths.daos;

import org.hibernate.criterion.DetachedCriteria;

import com.nm.auths.models.User;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderUser extends AbstractQueryBuilder<QueryBuilderUser> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QueryBuilderUser(Class<? extends User> clazz) {
		criteria = DetachedCriteria.forClass(clazz, getMainAlias());
	}

	private DetachedCriteria criteria;

	public static QueryBuilderUser get() {
		return new QueryBuilderUser(User.class);
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
