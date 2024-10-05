package com.nm.app.history;

import java.util.Date;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class DtoHistoryDefault implements DtoHistory {
	private Long id;
	private Date when;
	private Date created;
	private DtoHistoryActor actor;
	private DtoHistorySubject subject;

	public void setActor(DtoHistoryActor actor) {
		this.actor = actor;
	}

	public void setSubject(DtoHistorySubject subject) {
		this.subject = subject;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Long getId() {
		return id;
	}

	public Date getWhen() {
		return when;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setWhen(Date when) {
		this.when = when;
	}

	public DtoHistoryActor getActor() {
		return actor;
	}

	public DtoHistorySubject getSubject() {
		return subject;
	}

}
