package com.nm.social.daos;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.auths.constants.AuthenticationProvider;
import com.nm.social.models.SocialEvent;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderSocialEvent extends AbstractQueryBuilder<QueryBuilderSocialEvent> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QueryBuilderSocialEvent() {

	}

	private DetachedCriteria criteria = DetachedCriteria.forClass(SocialEvent.class, getMainAlias());

	public static QueryBuilderSocialEvent get() {
		return new QueryBuilderSocialEvent();
	}

	public QueryBuilderSocialEvent withAuthProvider(AuthenticationProvider type) {
		this.criteria.add(Restrictions.eq("type", type));
		return this;
	}

	public QueryBuilderSocialEvent withUUID(String uuid) {
		this.criteria.add(Restrictions.eq("uuid", uuid));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
