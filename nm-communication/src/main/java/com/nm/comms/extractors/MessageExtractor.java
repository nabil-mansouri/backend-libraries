package com.nm.comms.extractors;

import java.util.Collection;

import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.Message;
import com.nm.comms.models.MessageContent;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.hibernate.NotFoundException;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public abstract class MessageExtractor {
	public static MessageExtractor find(MessageContent c) throws NotFoundException {
		Collection<MessageExtractor> extractors =ApplicationUtils.getBeansCollection(MessageExtractor.class);
		for (MessageExtractor e : extractors) {
			if (e.accept(c)) {
				return e;
			}
		}
		throw new NotFoundException("Could not found extractor for content: " + c);
	}

	public abstract boolean accept(MessageContent content);

	public abstract void extract(MessageExtractPart part, Message communication, MessageContent content) throws Exception;

	public abstract void extract(MessageExtractPart part, Message communication, MessageContent content, CommunicationActor actor) throws Exception;

}
