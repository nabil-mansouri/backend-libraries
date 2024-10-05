package com.nm.comms.extractors;

import java.util.Collection;

import com.google.common.collect.Sets;
import com.nm.comms.constants.MessagePartType.MessagePartTypeDefault;
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.Message;
import com.nm.comms.models.MessageContent;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class MessageExtractBuilder {
	private Collection<MessageMetaExtractor> metas = Sets.newConcurrentHashSet();

	public void setMetas(Collection<MessageMetaExtractor> metas) {
		this.metas = metas;
	}

	public MessageExtract build(Message message) throws Exception {
		MessageExtract extract = new MessageExtract();
		//
		for (CommunicationActor actor : message.getReceivers()) {
			for (MessageMetaExtractor m : metas) {
				m.extract(extract, message, actor);
			}
			//
			for (MessageContent c : message.getContents()) {
				MessageExtractor founded = MessageExtractor.find(c);
				if (c.getType().equals(MessagePartTypeDefault.Joined)) {
					founded.extract(extract.createJoined(), message, c, actor);
				} else {
					founded.extract(extract.getOrCreatePart(c.getType()), message, c, actor);
				}
			}
		}
		return extract;
	}

	public MessageExtract build(Message message, CommunicationActor actor) throws Exception {
		MessageExtract extract = new MessageExtract();
		//
		for (MessageMetaExtractor m : metas) {
			m.extract(extract, message, actor);
		}
		//
		for (MessageContent c : message.getContents()) {
			MessageExtractor founded = MessageExtractor.find(c);
			if (c.getType().equals(MessagePartTypeDefault.Joined)) {
				founded.extract(extract.createJoined(), message, c, actor);
			} else {
				founded.extract(extract.getOrCreatePart(c.getType()), message, c, actor);
			}
		}
		return extract;
	}
}
