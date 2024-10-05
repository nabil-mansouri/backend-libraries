package com.nm.social.daos;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.auths.constants.AuthenticationProvider;
import com.nm.social.models.SocialNetwork;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderSocialNetwork extends AbstractQueryBuilder<QueryBuilderSocialNetwork> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QueryBuilderSocialNetwork() {

	}

	private DetachedCriteria criteria = DetachedCriteria.forClass(SocialNetwork.class, getMainAlias());

	public static QueryBuilderSocialNetwork get() {
		return new QueryBuilderSocialNetwork();
	}

	public QueryBuilderSocialNetwork withAuthProvider(AuthenticationProvider type) {
		this.criteria.add(Restrictions.eq("type", type));
		return this;
	}

	public QueryBuilderSocialNetwork withUUID(String uuid) {
		this.criteria.add(Restrictions.eq("uuid", uuid));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
