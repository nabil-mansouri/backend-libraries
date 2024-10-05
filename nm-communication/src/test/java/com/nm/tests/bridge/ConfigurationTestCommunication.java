package com.nm.tests.bridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.app.EnableApplicationModule;
import com.nm.comms.configurations.EnableCommunicationsModule;
import com.nm.config.EnableConfigModule;
import com.nm.datas.configurations.EnableDatasModule;
import com.nm.templates.configurations.EnableTemplatesModule;
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
@EnableTemplatesModule
@EnableApplicationModule()
@EnableCommunicationsModule
@EnableUtilsModule(enableDefaultDS = true)
public class ConfigurationTestCommunication {
	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(TemplateNameEnumTest.class);
		reg.put(TemplateArgsEnumTest.class);
	}

	@Bean
	public TemplateContextEmail templateContextEmail() {
		return new TemplateContextEmail();
	}

	@Bean
	public TemplateContextTest templateContextTest() {
		return new TemplateContextTest();
	}
}
