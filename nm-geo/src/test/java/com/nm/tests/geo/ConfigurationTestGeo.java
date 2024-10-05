package com.nm.tests.geo;
/**
 * 
 * @author Nabil MANSOURI
 *
 */

import org.springframework.context.annotation.Configuration;

import com.nm.app.EnableApplicationModule;
import com.nm.config.EnableConfigModule;
import com.nm.datas.configurations.EnableDatasModule;
import com.nm.geo.configurations.EnableGeoModule;
import com.nm.utils.configurations.EnableUtilsModule;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@EnableGeoModule
@EnableDatasModule()
@EnableConfigModule()
@EnableApplicationModule()
@EnableUtilsModule(enableDefaultDS = true)
public class ConfigurationTestGeo {

}
