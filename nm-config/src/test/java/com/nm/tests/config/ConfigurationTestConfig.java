package com.nm.tests.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.nm.config.EnableConfigModule;
import com.nm.tests.config.TestSoaConfig.ModuleConfigOther;
import com.nm.utils.configurations.EnableUtilsModule;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@EnableConfigModule
@EnableUtilsModule(enableDefaultDS = true)
public class ConfigurationTestConfig {
	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(ModuleConfigOther.class);
	}
}
