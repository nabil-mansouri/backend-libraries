package com.nm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.config.constants.AppConfigKeyDefault;
import com.nm.config.constants.ModuleConfigKeyDefault;
import com.nm.config.dao.DaoAppConfig;
import com.nm.config.dao.DaoAppConfigImpl;
import com.nm.config.dao.DaoModuleConfig;
import com.nm.config.dao.DaoModuleConfigImpl;
import com.nm.utils.db.DatabaseTemplateFactory;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationConfig {
	public static final String MODULE_NAME = "config";

	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(AppConfigKeyDefault.class);
		reg.put(ModuleConfigKeyDefault.class);
	}

	@Bean
	public SoaAppConfigImpl soaAppConfigImpl(DaoAppConfig dao) {
		SoaAppConfigImpl s = new SoaAppConfigImpl();
		s.setDaoAppConfig(dao);
		return s;
	}

	@Bean
	public SoaModuleConfigImpl soaModuleConfigImpl(DaoModuleConfig dao) {
		SoaModuleConfigImpl s = new SoaModuleConfigImpl();
		s.setDaoModuleConfig(dao);
		return s;
	}

	@Bean
	public DaoAppConfigImpl daoAppConfigImpl(DatabaseTemplateFactory fac) {
		DaoAppConfigImpl d = new DaoAppConfigImpl();
		d.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return d;
	}

	@Bean
	public DaoModuleConfigImpl daoModuleConfigImpl(DatabaseTemplateFactory fac) {
		DaoModuleConfigImpl d = new DaoModuleConfigImpl();
		d.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return d;
	}
}
