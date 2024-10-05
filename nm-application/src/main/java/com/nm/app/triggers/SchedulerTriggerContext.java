package com.nm.app.triggers;

import java.util.Date;

import org.springframework.context.ApplicationEvent;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class SchedulerTriggerContext {
	private Trigger trigger;
	private ApplicationEvent event;
	private Date created = new Date();

	public SchedulerTriggerContext() {
	}

	public SchedulerTriggerContext(Trigger trigger, ApplicationEvent event) {
		super();
		this.trigger = trigger;
		this.event = event;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}

	public ApplicationEvent getEvent() {
		return event;
	}

	public void setEvent(ApplicationEvent event) {
		this.event = event;
	}

}
