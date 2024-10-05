package com.nm.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.nm.app.EnableApplicationModule;
import com.nm.config.EnableConfigModule;
import com.nm.datas.configurations.EnableDatasModule;
import com.nm.tests.json.JsonEnumType;
import com.nm.utils.configurations.EnableUtilsModule;
import com.nm.utils.configurations.HibernateConfigPart;
import com.nm.utils.db.DatabaseTemplateFactory;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration()
@EnableDatasModule()
@EnableConfigModule()
@EnableUtilsModule(enableDefaultDS = true)
@EnableApplicationModule(enableTriggers = true)
@ComponentScan(basePackageClasses = ConfigurationTestApplication.class)
public class ConfigurationTestApplication {
	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(JsonEnumType.class);
	}

	/**
	 * Must have different name for method
	 * 
	 * @return
	 */
	@Bean
	public HibernateConfigPart hibernateConfigForTest() {
		HibernateConfigPart h = new HibernateConfigPart();
		h.setModuleName(DatabaseTemplateFactory.DEFAULT_HIBERNATE_NAME);
		h.add(getClass());
		return h;
	}
}
