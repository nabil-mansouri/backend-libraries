package com.nm.comms.senders;

import com.nm.comms.constants.CommunicationType;
import com.nm.comms.constants.CommunicationType.CommunicationTypeDefault;
import com.nm.comms.extractors.MessageExtract;
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.Message;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class MessageSenderSms implements MessageSender {

	public boolean accept(CommunicationType type) {
		if (type instanceof CommunicationTypeDefault) {
			return type.equals(CommunicationTypeDefault.Sms);
		}
		return false;
	}

	public void after(MessageExtract m, Message comm) throws Exception {

	}

	public void before(MessageExtract m, Message comm) throws Exception {

	}

	public void send(MessageExtract m, Message comm, CommunicationActor actor) throws Exception {
		// TODO
	}

}
