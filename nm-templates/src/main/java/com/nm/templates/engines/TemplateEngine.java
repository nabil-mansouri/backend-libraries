package com.nm.templates.engines;

import java.io.OutputStream;

import com.nm.templates.constants.TemplateType;
import com.nm.templates.contexts.TemplateContext;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface TemplateEngine {
	public boolean accept(TemplateType type);

	public TemplateContext buildContext();

	public OutputStream generate(TemplateModel template, TemplateContext context) throws Exception;

	public void generate(TemplateModel template, TemplateContext context, OutputStream output) throws Exception;
}
