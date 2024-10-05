package com.nm.templates.processors;

import java.io.OutputStream;

import com.nm.templates.contexts.TemplateContext;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface TemplateProcessorListener {
	public void generate(TemplateProcessor a, OutputStream out);

	public void onBuildContext(TemplateContext original);

}
