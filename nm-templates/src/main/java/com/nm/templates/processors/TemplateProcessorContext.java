package com.nm.templates.processors;

import com.nm.app.async.Priorisable;
import com.nm.templates.constants.TemplateArgsEnum;
import com.nm.templates.contexts.TemplateContextParameters;
import com.nm.templates.contexts.TemplateContextResults;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface TemplateProcessorContext extends Priorisable {

	public boolean accept(TemplateArgsEnum arg);

	public void prepare(TemplateContextParameters context);

	public void hydrate(TemplateContextResults context);

}
