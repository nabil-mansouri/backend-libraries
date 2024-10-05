package com.nm.config.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.config.constants.ModuleConfigKey;
import com.nm.config.model.ModuleConfig;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoModuleConfigImpl extends AbstractGenericDao<ModuleConfig, Long>implements DaoModuleConfig {

	@Override
	protected Class<ModuleConfig> getClassName() {
		return ModuleConfig.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

	public ModuleConfig findByKey(String module, ModuleConfigKey key) throws NoDataFoundException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ModuleConfig.class);
		criteria.add(Restrictions.eq("key", key));
		criteria.add(Restrictions.eq("module", module));
		@SuppressWarnings("unchecked")
		List<ModuleConfig> confgs = (List<ModuleConfig>) getHibernateTemplate().findByCriteria(criteria);
		if (confgs.isEmpty()) {
			throw new NoDataFoundException("No config founded with key" + key);
		}
		return confgs.iterator().next();
	}

}
