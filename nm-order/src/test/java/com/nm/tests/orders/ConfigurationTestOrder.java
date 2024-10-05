package com.nm.tests.orders;

import org.springframework.context.annotation.Configuration;

import com.nm.app.EnableApplicationModule;
import com.nm.config.EnableConfigModule;
import com.nm.datas.configurations.EnableDatasModule;
import com.nm.orders.configurations.EnableOrderModule;
import com.nm.paiments.configurations.EnablePaimentModule;
import com.nm.utils.configurations.EnableUtilsModule;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@EnableOrderModule
@EnableDatasModule()
@EnablePaimentModule
@EnableConfigModule()
@EnableApplicationModule()
@EnableUtilsModule(enableDefaultDS = true)
public class ConfigurationTestOrder {

}
