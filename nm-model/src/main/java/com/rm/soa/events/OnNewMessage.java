package com.rm.soa.events;

import org.springframework.context.ApplicationEvent;

import com.cm.comm.models.CommunicationMessage;

/**
 * 
 * @author Nabil
 * 
 */
public class OnNewMessage extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final CommunicationMessage message;

	public OnNewMessage(Object source, CommunicationMessage message) {
		super(source);
		this.message = message;
	}

	public CommunicationMessage getMessage() {
		return message;
	}

}
