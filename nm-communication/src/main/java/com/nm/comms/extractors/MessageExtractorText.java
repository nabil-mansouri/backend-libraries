package com.nm.comms.extractors;

import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.Message;
import com.nm.comms.models.MessageContent;
import com.nm.comms.models.MessageContentText;
import com.nm.utils.ByteUtils;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class MessageExtractorText extends MessageExtractor {

	public boolean accept(MessageContent content) {
		return content instanceof MessageContentText;
	}

	public void extract(MessageExtractPart part, Message communication, MessageContent content,
			CommunicationActor actor) throws Exception {
		MessageContentText f = (MessageContentText) content;
		part.create(ByteUtils.toBytes(f.getText()), actor);
	}

	public void extract(MessageExtractPart part, Message communication, MessageContent content) throws Exception {
		MessageContentText f = (MessageContentText) content;
		part.create(ByteUtils.toBytes(f.getText()));
	}

}
