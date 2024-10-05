package com.nm.comms.extractors;

import com.nm.comms.models.CommunicationActor;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class MessageExtractNode {
	private CommunicationActor actor;
	private byte[] content;

	public boolean hasActor() {
		return this.actor != null;
	}

	public CommunicationActor getActor() {
		return actor;
	}

	public void setActor(CommunicationActor actor) {
		this.actor = actor;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

}
