package com.nm.social.daos;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.auths.constants.AuthenticationProvider;
import com.nm.social.models.SocialCalendar;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderSocialCalendar extends AbstractQueryBuilder<QueryBuilderSocialCalendar> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QueryBuilderSocialCalendar() {

	}

	private DetachedCriteria criteria = DetachedCriteria.forClass(SocialCalendar.class, getMainAlias());

	public static QueryBuilderSocialCalendar get() {
		return new QueryBuilderSocialCalendar();
	}

	public QueryBuilderSocialCalendar withAuthProvider(AuthenticationProvider type) {
		this.criteria.add(Restrictions.eq("type", type));
		return this;
	}

	public QueryBuilderSocialCalendar withUUID(String uuid) {
		this.criteria.add(Restrictions.eq("uuid", uuid));
		return this;
	}

	public QueryBuilderSocialCalendar withOwnerId(String uuid) {
		createAlias("owners", "owners");
		this.criteria.add(Restrictions.eq("owners.uuid", uuid));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
