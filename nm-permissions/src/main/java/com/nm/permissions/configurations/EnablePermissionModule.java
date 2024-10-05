package com.nm.permissions.configurations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.nm.permissions.grants.PermissionGranterDefault;
import com.nm.utils.db.DatabaseTemplateFactory;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Import({ ConfigurationPermissionSelector.class, ConfigurationPermissionsHibernate.class })
public @interface EnablePermissionModule {
	Class<?>[]granter() default { PermissionGranterDefault.class };

	boolean enableAcl() default true;

	String hibernateName() default DatabaseTemplateFactory.DEFAULT_HIBERNATE_NAME;
}
