package com.nm.auths.configurations;

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
public class ConfigurationAuthenticationsSelector implements ImportSelector {

	private AnnotationAttributes attributes;

	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		Map<String, Object> annotationAttributes = importingClassMetadata
				.getAnnotationAttributes(EnableAuthenticationModule.class.getName());
		attributes = AnnotationAttributes.fromMap(annotationAttributes);
		String[] all = new String[0];
		all = ArrayUtils.add(all, ConfigurationAuthentications.class.getName());
		all = ArrayUtils.add(all, ConfigurationAuthenticationHibernate.class.getName());
		if (isEnableWebContext()) {
			all = ArrayUtils.add(all, ConfigurationAuthenticationsWebContext.class.getName());
		}
		return all;
	}

	public boolean isEnableWebContext() {
		return attributes.getBoolean("enableWebContext");
	}
}