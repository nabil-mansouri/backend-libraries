package com.nm.tests.permissions;
/**
 * 
 * @author Nabil MANSOURI
 *
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.app.EnableApplicationModule;
import com.nm.auths.configurations.EnableAuthenticationModule;
import com.nm.config.EnableConfigModule;
import com.nm.datas.configurations.EnableDatasModule;
import com.nm.permissions.configurations.EnablePermissionModule;
import com.nm.utils.configurations.EnableUtilsModule;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@EnableDatasModule()
@EnableConfigModule()
@EnablePermissionModule()
@EnableApplicationModule()
@EnableAuthenticationModule
@EnableUtilsModule(enableDefaultDS = true)
public class ConfigurationTestPermission {
	@Bean
	public FakeService servie() {
		return new FakeServiceImpl();
	}

}
