package com.nm.tests.bridge;

import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.CommunicationActorMail;
import com.nm.comms.templates.TemplateProcessorContextActorAbstract;
import com.nm.templates.contexts.TemplateContextParameters;
import com.nm.templates.contexts.TemplateContextResults;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class TemplateContextEmail extends TemplateProcessorContextActorAbstract {
	public void hydrate(TemplateContextResults context, CommunicationActor actor) {
		CommunicationActorMail mail = (CommunicationActorMail) actor;
		context.putResult("testEmailActor", mail.getEmail());
	}

	public void prepare(TemplateContextParameters context, CommunicationActor actor) {

	}

	public boolean accept(CommunicationActor actor) {
		return actor instanceof CommunicationActorMail;
	}

}
