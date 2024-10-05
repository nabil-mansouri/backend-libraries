package com.nm.tests.shop;

import org.springframework.context.annotation.Configuration;

import com.nm.app.EnableApplicationModule;
import com.nm.cms.configurations.EnableCmsModule;
import com.nm.config.EnableConfigModule;
import com.nm.datas.configurations.EnableDatasModule;
import com.nm.dictionnary.configurations.EnableDictionnaryModule;
import com.nm.geo.configurations.EnableGeoModule;
import com.nm.plannings.configurations.EnablePlanningModule;
import com.nm.shop.configurations.EnableShopModule;
import com.nm.utils.configurations.EnableUtilsModule;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@EnableGeoModule
@EnableCmsModule
@EnableShopModule
@EnableDatasModule()
@EnableConfigModule()
@EnablePlanningModule
@EnableDictionnaryModule
@EnableApplicationModule()
@EnableUtilsModule(enableDefaultDS = true)
public class ConfigurationTestShop {

}
