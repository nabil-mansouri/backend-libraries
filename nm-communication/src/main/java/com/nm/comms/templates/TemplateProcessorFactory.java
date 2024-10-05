package com.nm.comms.templates;

import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.Message;
import com.nm.templates.processors.TemplateProcessor;
import com.nm.templates.processors.TemplateProcessorListener;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class TemplateProcessorFactory {
	public static TemplateProcessor get(Message comm, TemplateProcessorListener l) {
		return new TemplateProcessorImplCommunication(l, comm);
	}

	public static TemplateProcessor get(Message comm, TemplateProcessorListener l, CommunicationActor actor) {
		return new TemplateProcessorImplCommunication(l, comm, actor);
	}
}
