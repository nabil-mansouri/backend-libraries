package com.nm.app.triggers;

import org.springframework.context.ApplicationEvent;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class EventTriggerOnEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TriggerEventEnum event;

	public EventTriggerOnEvent(Object source, TriggerEventEnum enu) {
		super(source);
		this.event = enu;
	}

	public TriggerEventEnum getEvent() {
		return event;
	}

}