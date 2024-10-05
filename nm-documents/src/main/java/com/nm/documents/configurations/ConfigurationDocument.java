package com.nm.documents.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.datas.SoaAppData;
import com.nm.documents.templates.TemplateEngineExcel;
import com.nm.documents.templates.TemplateEngineJsonSerialized;
import com.nm.documents.templates.TemplateEngineWord;
import com.nm.templates.engines.TemplateEngine;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationDocument {
	public static final String MODULE_NAME = "documents";

	@Bean
	public TemplateEngine templateEngineExcel(SoaAppData dao) {
		TemplateEngineExcel s = new TemplateEngineExcel();
		s.setSoaAppData(dao);
		return s;
	}

	@Bean
	public TemplateEngine templateEngineWord(SoaAppData dao) {
		TemplateEngineWord s = new TemplateEngineWord();
		s.setSoaAppData(dao);
		return s;
	}

	@Bean
	public TemplateEngine templateEngineJsonSerialized(SoaAppData dao) {
		TemplateEngineJsonSerialized s = new TemplateEngineJsonSerialized();
		return s;
	}

}
