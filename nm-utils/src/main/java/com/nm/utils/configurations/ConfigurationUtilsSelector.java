package com.nm.utils.configurations;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class ConfigurationUtilsSelector implements ImportSelector {

	private AnnotationAttributes attributes;

	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableUtilsModule.class.getName());
		attributes = AnnotationAttributes.fromMap(annotationAttributes);
		String[] all = new String[0];
		if (enableDefaultDS()) {
			all = ArrayUtils.add(all, ConfigurationDatasource.class.getName());
		}
		all = ArrayUtils.add(all, ConfigurationUtils.class.getName());
		all = ArrayUtils.add(all, ConfigurationUtilsDatabases.class.getName());
		all = ArrayUtils.add(all, ConfigurationUtilsHibernate.class.getName());
		all = ArrayUtils.add(all, ConfigurationUtilsExt.class.getName());
		return all;
	}

	public boolean enableDefaultDS() {
		return attributes.getBoolean("enableDefaultDS");
	}
}