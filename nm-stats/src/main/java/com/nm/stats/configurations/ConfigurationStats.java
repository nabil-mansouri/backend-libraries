package com.nm.stats.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.stats.models.DaoDates;
import com.nm.stats.models.DaoDatesImpl;
import com.nm.utils.db.DatabaseTemplateFactory;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationStats {
	public static final String MODULE_NAME = "stats";

	@Bean
	public DaoDates DaoDatesImpl(DatabaseTemplateFactory fac) {
		DaoDatesImpl d = new DaoDatesImpl();
		d.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return d;
	}
}
