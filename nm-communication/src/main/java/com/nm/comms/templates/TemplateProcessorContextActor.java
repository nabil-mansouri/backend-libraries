package com.nm.comms.templates;

import com.nm.app.async.Priorisable;
import com.nm.comms.models.CommunicationActor;
import com.nm.templates.contexts.TemplateContextParameters;
import com.nm.templates.contexts.TemplateContextResults;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface TemplateProcessorContextActor extends Priorisable {

	public boolean accept(CommunicationActor actor);

	public void prepare(TemplateContextParameters context, CommunicationActor actor);

	public void hydrate(TemplateContextResults context, CommunicationActor actor);

}
