package com.nm.config.dao;

import java.util.Collection;

import com.nm.config.constants.AppConfigKey;
import com.nm.config.model.AppConfig;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoAppConfigImpl extends AbstractGenericDao<AppConfig, Long>implements DaoAppConfig {

	@Override
	protected Class<AppConfig> getClassName() {
		return AppConfig.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

	public AppConfig findByKey(AppConfigKey key) throws NoDataFoundException {
		Collection<AppConfig> confgs = find(AppConfigQueryBuilder.get().withKey(key));
		if (confgs.isEmpty()) {
			throw new NoDataFoundException("No config founded with key" + key);
		}
		return confgs.iterator().next();
	}

}
