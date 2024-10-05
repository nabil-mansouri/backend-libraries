package com.nm.comms.extractors;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.Message;
import com.nm.comms.models.MessageContent;
import com.nm.comms.models.MessageContentTemplate;
import com.nm.comms.templates.TemplateProcessorFactory;
import com.nm.comms.templates.TemplateProcessorImplCommunication;
import com.nm.templates.contexts.TemplateContext;
import com.nm.templates.models.Template;
import com.nm.templates.processors.TemplateProcessor;
import com.nm.templates.processors.TemplateProcessorListener;
import com.nm.templates.processors.strategies.TemplateProcessorStrategyFactory;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class MessageExtractorTemplate extends MessageExtractor {

	public boolean accept(MessageContent content) {
		return content instanceof MessageContentTemplate;
	}

	public void extract(final MessageExtractPart part, Message communication, MessageContent content,
			CommunicationActor actor) throws Exception {
		MessageContentTemplate f = (MessageContentTemplate) content;
		Template template = f.getTemplate();
		TemplateProcessorFactory.get(communication, new TemplateProcessorListener() {
			public void onBuildContext(TemplateContext original) {

			}

			public void generate(TemplateProcessor a, OutputStream out) {
				ByteArrayOutputStream bArray = (ByteArrayOutputStream) (out);
				if (a instanceof TemplateProcessorImplCommunication) {
					part.create(bArray.toByteArray(), ((TemplateProcessorImplCommunication) a).getCurrentActor());
				} else {
					part.create(bArray.toByteArray());
				}
			}
		}, actor).generate(TemplateProcessorStrategyFactory.defaultStrategy(template));
	}

	public void extract(final MessageExtractPart part, Message communication, MessageContent content) throws Exception {
		MessageContentTemplate f = (MessageContentTemplate) content;
		Template template = f.getTemplate();
		TemplateProcessorFactory.get(communication, new TemplateProcessorListener() {
			public void onBuildContext(TemplateContext original) {

			}

			public void generate(TemplateProcessor a, OutputStream out) {
				ByteArrayOutputStream bArray = (ByteArrayOutputStream) (out);
				if (a instanceof TemplateProcessorImplCommunication) {
					part.create(bArray.toByteArray(), ((TemplateProcessorImplCommunication) a).getCurrentActor());
				} else {
					part.create(bArray.toByteArray());
				}
			}
		}).generate(TemplateProcessorStrategyFactory.defaultStrategy(template));
	}

}
