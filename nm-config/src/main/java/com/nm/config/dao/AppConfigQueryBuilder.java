package com.nm.config.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.config.constants.AppConfigKey;
import com.nm.config.model.AppConfig;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class AppConfigQueryBuilder extends AbstractQueryBuilder<AppConfigQueryBuilder> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AppConfigQueryBuilder() {

	}

	private DetachedCriteria criteria = DetachedCriteria.forClass(AppConfig.class, getMainAlias());

	public static AppConfigQueryBuilder get() {
		return new AppConfigQueryBuilder();
	}

	public AppConfigQueryBuilder withKey(AppConfigKey key) {
		this.criteria.add(Restrictions.eq("key", key));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
