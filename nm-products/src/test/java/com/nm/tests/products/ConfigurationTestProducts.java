package com.nm.tests.products;

import org.springframework.context.annotation.Configuration;

import com.nm.app.EnableApplicationModule;
import com.nm.cms.configurations.EnableCmsModule;
import com.nm.config.EnableConfigModule;
import com.nm.datas.configurations.EnableDatasModule;
import com.nm.dictionnary.configurations.EnableDictionnaryModule;
import com.nm.products.configuration.EnableProductsModule;
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
@EnableProductsModule
@EnableDictionnaryModule
@EnableApplicationModule()
@EnableUtilsModule(enableDefaultDS = true)
public class ConfigurationTestProducts {

}
