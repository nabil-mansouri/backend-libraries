package com.nm.permissions.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.annotation.AnnotationMetadataExtractor;
import org.springframework.security.access.annotation.SecuredAnnotationSecurityMetadataSource;

import com.google.common.collect.Lists;
import com.nm.permissions.grants.PermissionAuthorityGranter;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class CustomMethodSecurityMetadataSource extends SecuredAnnotationSecurityMetadataSource {

	private Collection<CustomMethodSecurityListener> listeners = Lists.newArrayList();

	public CustomMethodSecurityMetadataSource setListeners(Collection<CustomMethodSecurityListener> listeners) {
		this.listeners = listeners;
		return this;
	}

	@Override
	protected Collection<ConfigAttribute> findAttributes(Class<?> clazz) {
		Collection<ConfigAttribute> all = Lists.newArrayList();
		for (CustomMethodSecurityListener l : listeners) {
			for (Class<? extends Annotation> c : l.classes()) {
				all.addAll(processAnnotation(AnnotationUtils.findAnnotation(clazz, c)));
			}
		}
		return all;
	}

	@Override
	protected Collection<ConfigAttribute> findAttributes(Method method, Class<?> targetClass) {
		Collection<ConfigAttribute> all = Lists.newArrayList();
		for (CustomMethodSecurityListener l : listeners) {
			for (Class<? extends Annotation> c : l.classes()) {
				all.addAll(processAnnotation(AnnotationUtils.findAnnotation(method, c)));
			}
		}
		return all;
	}

	@SuppressWarnings("unchecked")
	private Collection<ConfigAttribute> processAnnotation(Annotation a) {
		if (a == null) {
			return new ArrayList<>();
		}
		return (Collection<ConfigAttribute>) extractor().extractAttributes(a);
	}

	protected AnnotationMetadataExtractor<Annotation> extractor() {
		return new AnnotationMetadataExtractor<Annotation>() {

			@Override
			public Collection<? extends ConfigAttribute> extractAttributes(Annotation securityAnnotation) {
				List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>();
				if (securityAnnotation != null) {
					for (CustomMethodSecurityListener l : listeners) {
						if (l.classes().contains(securityAnnotation.annotationType())) {
							for (Annotation secured : l.multiple(securityAnnotation)) {
								attributes.add(new SecurityConfig(PermissionAuthorityGranter
										.authorityName(l.resource(secured), l.action(secured))));
							}
						}
					}
				}
				return attributes;
			}
		};
	}

}
