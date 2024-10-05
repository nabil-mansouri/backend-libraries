package com.nm.app.triggers;

import org.springframework.context.ApplicationEvent;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class EventTriggerSchedule extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Trigger trigger;

	public EventTriggerSchedule(Object source, Trigger trigger) {
		super(source);
		this.trigger = trigger;
	}

	public Trigger getTrigger() {
		return trigger;
	}

}