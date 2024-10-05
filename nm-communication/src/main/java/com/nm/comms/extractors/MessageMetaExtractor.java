package com.nm.comms.extractors;

import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.Message;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface MessageMetaExtractor {

	public void extract(MessageExtract extr, Message mess) throws Exception;

	public void extract(MessageExtract extr, Message mess, CommunicationActor actor) throws Exception;

}
