package com.nm.app;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.nm.utils.db.DatabaseTemplateFactory;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Import({ ConfigurationApplicationSelector.class })
public @interface EnableApplicationModule {

	boolean enableTriggers() default true;

	String hibernateName() default DatabaseTemplateFactory.DEFAULT_HIBERNATE_NAME;
}
