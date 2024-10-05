package com.nm.social.daos;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.auths.constants.AuthenticationProvider;
import com.nm.social.models.SocialUser;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderSocialUser extends AbstractQueryBuilder<QueryBuilderSocialUser> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QueryBuilderSocialUser() {

	}

	private DetachedCriteria criteria = DetachedCriteria.forClass(SocialUser.class, getMainAlias());

	public static QueryBuilderSocialUser get() {
		return new QueryBuilderSocialUser();
	}

	public QueryBuilderSocialUser withAuthProvider(AuthenticationProvider type) {
		this.criteria.add(Restrictions.eq("type", type));
		return this;
	}

	public QueryBuilderSocialUser withUUID(long uuid) {
		this.criteria.add(Restrictions.eq("uuid", String.valueOf(uuid)));
		return this;
	}

	public QueryBuilderSocialUser withUUID(String uuid) {
		this.criteria.add(Restrictions.eq("uuid", uuid));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
