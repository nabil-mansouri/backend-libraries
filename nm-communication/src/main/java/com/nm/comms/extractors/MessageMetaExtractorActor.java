package com.nm.comms.extractors;

import com.nm.comms.constants.MessageMetaExtractorKey.MessageMetaExtractorKeyDefault;
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.CommunicationActorMail;
import com.nm.comms.models.Message;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class MessageMetaExtractorActor implements MessageMetaExtractor {

	public void extract(MessageExtract extr, Message mess, CommunicationActor actor) throws Exception {
		if (actor instanceof CommunicationActorMail) {
			extr.getOrCreateMeta(actor).put(MessageMetaExtractorKeyDefault.MailTo,
					((CommunicationActorMail) actor).getEmail());
		}
	}

	public void extract(MessageExtract extr, Message mess) throws Exception {
		for (CommunicationActor ac : mess.getReceivers()) {
			extract(extr, mess, ac);
		}
	}

}
