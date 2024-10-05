package com.nm.templates.processors;

import com.nm.templates.contexts.TemplateContext;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class TemplateProcessorImplSimple extends TemplateProcessorAbstract {

	public TemplateProcessorImplSimple(TemplateProcessorListener listener) {
		super(listener);
	}

	@Override
	protected void onContextReady(TemplateContext original, TemplateProcessorAction action) throws Exception {
		action.doGenerate(original);
	}

}
