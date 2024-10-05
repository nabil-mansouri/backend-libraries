package com.nm.tests.dictionnary;
/**
 * 
 * @author Nabil MANSOURI
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.nm.app.EnableApplicationModule;
import com.nm.config.EnableConfigModule;
import com.nm.datas.configurations.EnableDatasModule;
import com.nm.dictionnary.configurations.EnableDictionnaryModule;
import com.nm.utils.configurations.EnableUtilsModule;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@EnableDatasModule()
@EnableConfigModule()
@EnableDictionnaryModule
@EnableApplicationModule()
@EnableUtilsModule(enableDefaultDS = true)
public class ConfigurationTestDictionnary {
	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(EnumDictionnaryDomainTest.class);
	}
}
