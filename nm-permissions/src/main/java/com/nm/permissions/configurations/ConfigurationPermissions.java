package com.nm.permissions.configurations;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import com.nm.auths.configurations.EnableAuthenticationModule;
import com.nm.permissions.SoaPermission;
import com.nm.permissions.SoaPermissionImpl;
import com.nm.permissions.annotations.CustomMethodSecurityListener;
import com.nm.permissions.annotations.CustomMethodSecurityMetadataSource;
import com.nm.permissions.annotations.CustomMethodSecurityMetadataSourceDefault;
import com.nm.permissions.constants.ActionDefault;
import com.nm.permissions.constants.ResourceTypeDefault;
import com.nm.permissions.converters.PermissionFormConverter;
import com.nm.permissions.converters.PermissionViewConverter;
import com.nm.permissions.converters.ResourceConverter;
import com.nm.permissions.converters.SubjectConverter;
import com.nm.permissions.daos.DaoPermission;
import com.nm.permissions.daos.DaoPermissionImpl;
import com.nm.permissions.daos.DaoResource;
import com.nm.permissions.daos.DaoResourceImpl;
import com.nm.utils.db.DatabaseTemplateFactory;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@EnableAuthenticationModule
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ConfigurationPermissions extends GlobalMethodSecurityConfiguration {
	public static final String MODULE_NAME = "permissions";
	//
	private Collection<CustomMethodSecurityListener> listeners;

	@Autowired
	public void setListeners(Collection<CustomMethodSecurityListener> listeners) {
		this.listeners = listeners;
	}

	@Bean
	public CustomMethodSecurityMetadataSourceDefault customMethodSecurityMetadataSourceDefault() {
		return new CustomMethodSecurityMetadataSourceDefault();
	}

	protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
		return new CustomMethodSecurityMetadataSource().setListeners(listeners);
	}

	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(ActionDefault.class);
		reg.put(ResourceTypeDefault.class);
	}

	@Bean
	public PermissionFormConverter permissionFormConverter(DtoConverterRegistry registry, DaoPermission daoP) {
		PermissionFormConverter form = new PermissionFormConverter();
		form.setDao(daoP);
		form.setRegistry(registry);
		return form;
	}

	@Bean
	public PermissionViewConverter permissionViewConverter(DaoPermission daoP) {
		PermissionViewConverter view = new PermissionViewConverter();
		view.setDao(daoP);
		return view;
	}

	@Bean
	public ResourceConverter resourceConverter() {
		return new ResourceConverter();
	}

	@Bean
	public SubjectConverter subjectConverter() {
		return new SubjectConverter();
	}

	@Bean
	public DaoPermission doaPermission(DatabaseTemplateFactory factory) {
		DaoPermissionImpl p = new DaoPermissionImpl();
		p.setHibernateTemplate(factory.hibernateResource(MODULE_NAME));
		return p;
	}

	@Bean
	public DaoResource daoResource(DatabaseTemplateFactory factory) {
		DaoResourceImpl p = new DaoResourceImpl();
		p.setHibernateTemplate(factory.hibernateResource(MODULE_NAME));
		return p;
	}

	@Bean
	public SoaPermission soaPermissionImpl(DaoPermission dao, DtoConverterRegistry reg) {
		SoaPermissionImpl soa = new SoaPermissionImpl();
		soa.setDao(dao);
		soa.setRegistry(reg);
		return soa;
	}

}
