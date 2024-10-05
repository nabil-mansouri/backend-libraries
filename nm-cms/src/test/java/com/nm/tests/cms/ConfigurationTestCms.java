package com.nm.tests.cms;
/**
 * 
 * @author Nabil MANSOURI
 *
 */

import org.springframework.context.annotation.Configuration;

import com.nm.app.EnableApplicationModule;
import com.nm.cms.configurations.EnableCmsModule;
import com.nm.config.EnableConfigModule;
import com.nm.datas.configurations.EnableDatasModule;
import com.nm.utils.configurations.EnableUtilsModule;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@EnableCmsModule
@EnableDatasModule()
@EnableConfigModule()
@EnableApplicationModule()
@EnableUtilsModule(enableDefaultDS = true)
public class ConfigurationTestCms {

}
