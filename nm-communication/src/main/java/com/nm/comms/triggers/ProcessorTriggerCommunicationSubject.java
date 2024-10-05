package com.nm.comms.triggers;

import java.util.Collection;

import com.nm.app.triggers.ProcessorTriggerSubject;
import com.nm.app.triggers.TriggerSubject;
import com.nm.comms.extractors.MessageExtract;
import com.nm.comms.extractors.MessageExtractBuilder;
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.Message;
import com.nm.comms.models.MessageTrigger;
import com.nm.comms.senders.MessageSender;
import com.nm.comms.senders.MessageSenderFactory;

/**
 * 
 * @author Nabil
 * 
 */
public class ProcessorTriggerCommunicationSubject implements ProcessorTriggerSubject {
	private MessageExtractBuilder extractor;

	public void setExtractor(MessageExtractBuilder extractor) {
		this.extractor = extractor;
	}

	public boolean accept(TriggerSubject subject) {
		return subject instanceof MessageTrigger;
	}

	public void process(TriggerSubject subject) throws Exception {
		MessageTrigger t = (MessageTrigger) subject;
		Message c = t.getMessage();
		MessageExtract extract = extractor.build(c);
		Collection<MessageSender> senders = MessageSenderFactory.find(c);
		for (MessageSender s : senders) {
			s.before(extract, c);
			for (CommunicationActor actor : c.getReceivers()) {
				s.send(extract, c, actor);
			}
			s.after(extract, c);
		}
	}

}
