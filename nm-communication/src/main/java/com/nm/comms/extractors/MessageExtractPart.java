package com.nm.comms.extractors;

import java.util.HashMap;

import com.nm.comms.models.CommunicationActor;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class MessageExtractPart extends HashMap<Long, MessageExtractNode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MessageExtractNode create(byte[] content) {
		MessageExtractNode n = new MessageExtractNode();
		n.setContent(content);
		this.put(null, n);
		return n;
	}

	public MessageExtractNode create(byte[] content, CommunicationActor actor) {
		MessageExtractNode n = new MessageExtractNode();
		n.setContent(content);
		n.setActor(actor);
		if (this.containsKey(actor.getId())) {
			throw new IllegalArgumentException("Acotr already saved in part : " + actor);
		}
		this.put(actor.getId(), n);
		return n;
	}

	public MessageExtractNode getBy(CommunicationActor actor) {
		return this.get(actor.getId());
	}

	public MessageExtractNode get() {
		return this.get(null);
	}
}
