package com.nm.app;

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
public class ConfigurationApplicationSelector implements ImportSelector {

	private AnnotationAttributes attributes;

	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		Map<String, Object> annotationAttributes = importingClassMetadata
				.getAnnotationAttributes(EnableApplicationModule.class.getName());
		attributes = AnnotationAttributes.fromMap(annotationAttributes);
		String[] all = new String[0];
		if (isTriggersEnabled()) {
			all = ArrayUtils.add(all, ConfigurationApplicationTrigger.class.getName());
		}
		all = ArrayUtils.add(all, ConfigurationApplication.class.getName());
		all = ArrayUtils.add(all, ConfigurationApplicationHibernate.class.getName());
		return all;
	}

	public boolean isTriggersEnabled() {
		return attributes.getBoolean("enableTriggers");
	}
}