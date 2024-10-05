package com.nm.tests.bridge;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.app.EnableApplicationModule;
import com.nm.carts.configurations.EnableCartModule;
import com.nm.config.EnableConfigModule;
import com.nm.datas.configurations.EnableDatasModule;
import com.nm.utils.configurations.EnableUtilsModule;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration 
@EnableCartModule
@EnableDatasModule()
@EnableConfigModule()
@EnableApplicationModule() 
@EnableUtilsModule(enableDefaultDS = true)
public class ConfigurationTestCart {
	@Bean
	public CartExtConverterImpl cartExtConverterImpl() {
		return new CartExtConverterImpl();
	}
}
