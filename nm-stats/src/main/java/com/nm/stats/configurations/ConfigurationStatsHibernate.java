package com.nm.stats.configurations;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import com.nm.stats.models.Dates;
import com.nm.utils.configurations.HibernateConfigPart;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationStatsHibernate implements ImportAware {

	private String module;

	/**
	 * Must have different name for method
	 * 
	 * @return
	 */
	@Bean
	public HibernateConfigPart hibernateConfigForStats() {
		HibernateConfigPart h = new HibernateConfigPart();
		h.setModuleName(module);
		h.add(Dates.class);
		return h;
	}

	public void setImportMetadata(AnnotationMetadata importMetadata) {
		Map<String, Object> enableAttrMap = importMetadata.getAnnotationAttributes(EnableStatsModule.class.getName());
		AnnotationAttributes enableAttrs = AnnotationAttributes.fromMap(enableAttrMap);
		this.module = enableAttrs.getString("hibernateName");
	}
}
