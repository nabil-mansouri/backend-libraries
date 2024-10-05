package com.nm.auths.daos;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.auths.models.UserGroup;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderUserGroup extends AbstractQueryBuilder<QueryBuilderUserGroup> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QueryBuilderUserGroup(Class<? extends UserGroup> clazz) {
		criteria = DetachedCriteria.forClass(clazz, getMainAlias());
	}

	private DetachedCriteria criteria;

	public static QueryBuilderUserGroup get() {
		return new QueryBuilderUserGroup(UserGroup.class);
	}

	public QueryBuilderUserGroup withCode(String code) {
		this.criteria.add(Restrictions.eq("code", code));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
