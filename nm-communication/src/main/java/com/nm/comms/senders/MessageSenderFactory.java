package com.nm.comms.senders;

import java.util.Collection;

import com.google.common.collect.Sets;
import com.nm.comms.models.CommunicationMedium;
import com.nm.comms.models.Message;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.hibernate.NotFoundException;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class MessageSenderFactory {
	public static Collection<MessageSender> find(Message c) throws NotFoundException {
		Collection<MessageSender> senders = ApplicationUtils.getBeansCollection(MessageSender.class);
		Collection<MessageSender> s = Sets.newConcurrentHashSet();
		for (MessageSender e : senders) {
			for (CommunicationMedium m : c.getMediums()) {
				if (e.accept(m.getType())) {
					s.add(e);
				}
			}
		}
		if (s.isEmpty()) {
			throw new NotFoundException("Could not found sender for message: " + c);
		}
		return s;
	}
}
