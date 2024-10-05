package com.nm.comms.senders;

import com.nm.comms.constants.CommunicationType;
import com.nm.comms.extractors.MessageExtract;
import com.nm.comms.models.Message;
import com.nm.comms.models.CommunicationActor;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public interface MessageSender {
	public boolean accept(CommunicationType type);

	public void before(MessageExtract m, Message comm) throws Exception;

	public void send(MessageExtract m, Message comm, CommunicationActor actor) throws Exception;

	public void after(MessageExtract m, Message comm) throws Exception;
}
