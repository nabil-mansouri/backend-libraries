package com.nm.templates.processors.strategies;

import com.nm.templates.TemplateException;
import com.nm.templates.contexts.TemplateContext;
import com.nm.templates.engines.TemplateEngine;
import com.nm.templates.models.Template;
import com.nm.utils.hibernate.NotFoundException;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public interface TemplateProcessorStrategy {
	TemplateContext getContext();

	TemplateEngine getEngine();

	Template getTemplate();

	boolean acceptSubTemplate(Template template);

	void before() throws NotFoundException;

	void prepare() throws TemplateException;

	void hydrate() throws TemplateException;

}