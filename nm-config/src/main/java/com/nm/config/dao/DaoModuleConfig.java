package com.nm.config.dao;

import com.nm.config.constants.ModuleConfigKey;
import com.nm.config.model.ModuleConfig;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * Category dao
 * 
 * @author diallo
 * 
 */
public interface DaoModuleConfig extends IGenericDao<ModuleConfig, Long> {

	public ModuleConfig findByKey(String module, ModuleConfigKey key) throws NoDataFoundException;
}
