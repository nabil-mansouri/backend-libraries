package com.nm.tests;
/**
 * 
 * @author Nabil MANSOURI
 *
 */

import org.springframework.context.annotation.Configuration;

import com.nm.app.EnableApplicationModule;
import com.nm.auths.configurations.EnableAuthenticationModule;
import com.nm.config.EnableConfigModule;
import com.nm.datas.configurations.EnableDatasModule;
import com.nm.utils.configurations.EnableUtilsModule;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@EnableDatasModule()
@EnableConfigModule()
@EnableApplicationModule()
@EnableAuthenticationModule
@EnableUtilsModule(enableDefaultDS = true)
public class ConfigurationTestAuth {

}
