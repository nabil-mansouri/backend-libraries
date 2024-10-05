package com.nm.templates.processors;

import com.nm.templates.TemplateException;
import com.nm.templates.contexts.TemplateContext;
import com.nm.templates.processors.strategies.TemplateProcessorStrategy;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public interface TemplateProcessor {
	TemplateContext getCurrentContext();

	void generate(TemplateProcessorStrategy strategy) throws TemplateException;

}