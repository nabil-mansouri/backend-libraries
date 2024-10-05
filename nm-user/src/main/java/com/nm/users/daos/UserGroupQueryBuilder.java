package com.nm.users.daos;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.users.dtos.UserFilter;
import com.nm.users.models.User;
import com.nm.users.models.UserGroup;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class UserGroupQueryBuilder extends AbstractQueryBuilder<UserGroupQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DetachedCriteria criteria = DetachedCriteria.forClass(UserGroup.class);

	public static UserGroupQueryBuilder get() {
		return new UserGroupQueryBuilder();
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

	public UserGroupQueryBuilder withName(String id) {
		criteria.add(Restrictions.eq("name", id));
		return this;
	}

	public UserGroupQueryBuilder withId(Long id) {
		criteria.add(Restrictions.idEq(id));
		return this;
	}

	public UserGroupQueryBuilder withNotId(Long id) {
		criteria.add(Restrictions.not(Restrictions.idEq(id)));
		return this;
	}

	public UserGroupQueryBuilder withUser(User user) {
		criteria.createAlias("users", "users");
		criteria.add(Restrictions.eq("users.id", user.getId()));
		return this;
	}

	public UserGroupQueryBuilder withAccount(Long account) {
		createAlias("users", "users");
		createAlias("users.account", "account");
		criteria.add(Restrictions.eq("account.id", account));
		return this;
	}

	public UserGroupQueryBuilder withFilter(UserFilter filter) {
		if (filter.getId() != null) {
			withUser(filter.getId());
		}
		if (filter.getIdAccount() != null) {
			withAccount(filter.getIdAccount());
		}
		return this;
	}

	public UserGroupQueryBuilder withUser(Long user) {
		createAlias("users", "users");
		criteria.add(Restrictions.eq("users.id", user));
		return this;
	}
}
