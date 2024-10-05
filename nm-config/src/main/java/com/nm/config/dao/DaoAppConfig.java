package com.nm.config.dao;

import com.nm.config.constants.AppConfigKey;
import com.nm.config.model.AppConfig;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author MANSOURI NAbil
 * 
 */
public interface DaoAppConfig extends IGenericDao<AppConfig, Long> {

	public AppConfig findByKey(AppConfigKey key) throws NoDataFoundException;
}
