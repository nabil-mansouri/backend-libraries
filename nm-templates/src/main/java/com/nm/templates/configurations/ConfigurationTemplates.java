package com.nm.templates.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.datas.SoaAppData;
import com.nm.datas.daos.DaoAppData;
import com.nm.templates.SoaTemplate;
import com.nm.templates.SoaTemplateImpl;
import com.nm.templates.constants.TemplateArgsEnum.TemplateArgsEnumDefault;
import com.nm.templates.constants.TemplateNameEnum.TemplateNameEnumDefault;
import com.nm.templates.constants.TemplateType.TemplateTypeDefault;
import com.nm.templates.converters.TemplateContentConverterImpl;
import com.nm.templates.engines.TemplateEngine;
import com.nm.templates.engines.TemplateEngineVelocity;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationTemplates {
	public static final String MODULE_NAME = "templates";

	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(TemplateArgsEnumDefault.class);
		reg.put(TemplateNameEnumDefault.class);
		reg.put(TemplateTypeDefault.class);
	}

	@Bean
	public TemplateContentConverterImpl templateContentConverterImpl(DaoAppData dao) {
		TemplateContentConverterImpl d = new TemplateContentConverterImpl();
		d.setDao(dao);
		return d;
	}

	@Bean
	public TemplateEngine templateEngineVelocity(SoaAppData dao) {
		TemplateEngineVelocity d = new TemplateEngineVelocity();
		d.setSoaAppData(dao);
		return d;
	}

	@Bean
	public SoaTemplate soaTemplateImpl(DtoConverterRegistry registry) {
		SoaTemplateImpl d = new SoaTemplateImpl();
		d.setRegistry(registry);
		return d;
	}
}
