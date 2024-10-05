package com.nm.app.triggers;

import java.util.Date;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class DtoTriggerDefault implements DtoTrigger {
	public static enum DtoTriggerDefaultWhen {
		Now, Date, Cron, Event
	}

	private static final long serialVersionUID = 1L;
	private String cron;
	private Date date;
	private DtoTriggerDefaultWhen when;
	private TriggerEventEnum event;
	private Long id;

	public TriggerEventEnum getEvent() {
		return event;
	}

	public void setEvent(TriggerEventEnum event) {
		this.event = event;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public DtoTriggerDefaultWhen getWhen() {
		return when;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public DtoTriggerDefault setWhen(DtoTriggerDefaultWhen when) {
		this.when = when;
		return this;
	}

	public String getCron() {
		return cron;
	}

	public DtoTriggerDefault setCron(String cron) {
		this.cron = cron;
		return this;
	}
}