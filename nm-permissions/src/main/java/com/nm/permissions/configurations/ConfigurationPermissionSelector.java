package com.nm.permissions.configurations;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import com.nm.permissions.grants.PermissionGranterDefault;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class ConfigurationPermissionSelector implements ImportSelector {

	private AnnotationAttributes attributes;

	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		Map<String, Object> annotationAttributes = importingClassMetadata
				.getAnnotationAttributes(EnablePermissionModule.class.getName());
		attributes = AnnotationAttributes.fromMap(annotationAttributes);
		String[] all = new String[0];
		if (isAclEnabled()) {
			all = ArrayUtils.add(all, ConfigurationPermissionsAcl.class.getName());
		}
		for (Class<?> c : grantersClass()) {
			if (c.equals(PermissionGranterDefault.class)) {
				all = ArrayUtils.add(all, ConfigurationPermissionsGranterDefaut.class.getName());
			}
		}
		all = ArrayUtils.add(all, ConfigurationPermissions.class.getName());
		return all;
	}

	private Class<?>[] grantersClass() {
		return attributes.getClassArray("granter");
	}

	public boolean isAclEnabled() {
		return attributes.getBoolean("enableAcl");
	}
}