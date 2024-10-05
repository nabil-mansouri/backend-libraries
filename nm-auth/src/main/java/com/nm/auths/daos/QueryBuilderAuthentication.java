package com.nm.auths.daos;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.auths.constants.AuthenticationState;
import com.nm.auths.constants.AuthenticationType;
import com.nm.auths.models.Authentication;
import com.nm.auths.models.AuthenticationOpenID;
import com.nm.auths.models.AuthenticationSimple;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderAuthentication extends AbstractQueryBuilder<QueryBuilderAuthentication> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QueryBuilderAuthentication(Class<? extends Authentication> clazz) {
		criteria = DetachedCriteria.forClass(Authentication.class, getMainAlias());
	}

	private DetachedCriteria criteria;

	public static QueryBuilderAuthentication get() {
		return new QueryBuilderAuthentication(Authentication.class);
	}

	public static QueryBuilderAuthentication getSimple() {
		return new QueryBuilderAuthentication(AuthenticationSimple.class);
	}

	public static QueryBuilderAuthentication getOpenID() {
		return new QueryBuilderAuthentication(AuthenticationOpenID.class);
	}

	public QueryBuilderAuthentication withState(AuthenticationState state) {
		this.criteria.add(Restrictions.eq("state", state));
		return this;
	}

	public QueryBuilderAuthentication withType(AuthenticationType type) {
		this.criteria.add(Restrictions.eq("type", type));
		return this;
	}

	public QueryBuilderAuthentication withOpenID(String openid) {
		this.criteria.add(Restrictions.eq("openid", openid));
		return this;
	}

	public QueryBuilderAuthentication withUsername(String username) {
		this.criteria.add(Restrictions.eq("username", username));
		return this;
	}

	public QueryBuilderAuthentication withPassword(String password) {
		this.criteria.add(Restrictions.eq("password", password));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
