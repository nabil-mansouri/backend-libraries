package com.nm.users.daos;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.nm.users.models.User;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class UserQueryBuilder extends AbstractQueryBuilder<UserQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DetachedCriteria criteria = DetachedCriteria.forClass(User.class);

	public static UserQueryBuilder get() {
		return new UserQueryBuilder();
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

	public UserQueryBuilder withMaster(boolean master) {
		criteria.add(Restrictions.eq("master", master));
		return this;
	}

	public UserQueryBuilder withId(Long id) {
		criteria.add(Restrictions.idEq(id));
		return this;
	}

	public UserQueryBuilder withNotId(Long id) {
		criteria.add(Restrictions.not(Restrictions.idEq(id)));
		return this;
	}

	public UserQueryBuilder withAccount(Long id) {
		criteria.createAlias("account", "account");
		criteria.add(Restrictions.eq("account.id", id));
		return this;
	}

	public UserQueryBuilder withGroup(UserGroupQueryBuilder query) {
		query.createAlias("users", "users");
		DetachedCriteria sub = query.getQuery();
		sub.setProjection(Projections.property("users.id"));
		//
		criteria.add(Subqueries.propertyIn("id", sub));
		return this;
	}

	public UserQueryBuilder withId(Collection<Long> ids) {
		criteria.add(Restrictions.in("id", ids));
		return this;
	}
}
