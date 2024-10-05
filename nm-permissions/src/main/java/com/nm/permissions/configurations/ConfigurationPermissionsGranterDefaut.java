package com.nm.permissions.configurations;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.permissions.grants.PermissionGranterDefault;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationPermissionsGranterDefaut {

	@Bean(autowire = Autowire.BY_TYPE)
	public PermissionGranterDefault granter() {
		return new PermissionGranterDefault();
	}

}
