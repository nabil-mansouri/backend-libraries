package com.nm.paiments.configurations;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import com.nm.paiments.models.Transaction;
import com.nm.utils.configurations.HibernateConfigPart;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationPaimentHibernate implements ImportAware {

	private String module;

	/**
	 * Must have different name for method
	 * 
	 * @return
	 */
	@Bean
	public HibernateConfigPart hibernateConfigForPaiment() {
		HibernateConfigPart h = new HibernateConfigPart();
		h.setModuleName(module);
		h.add(Transaction.class);
		return h;
	}

	public void setImportMetadata(AnnotationMetadata importMetadata) {
		Map<String, Object> enableAttrMap = importMetadata.getAnnotationAttributes(EnablePaimentModule.class.getName());
		AnnotationAttributes enableAttrs = AnnotationAttributes.fromMap(enableAttrMap);
		this.module = enableAttrs.getString("hibernateName");
	}
}
