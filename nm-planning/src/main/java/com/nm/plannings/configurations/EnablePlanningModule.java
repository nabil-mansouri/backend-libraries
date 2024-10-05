package com.nm.plannings.configurations;

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
@Import({ ConfigurationPlanning.class, ConfigurationPlanningHibernate.class })
public @interface EnablePlanningModule {
	String hibernateName() default DatabaseTemplateFactory.DEFAULT_HIBERNATE_NAME;
}
