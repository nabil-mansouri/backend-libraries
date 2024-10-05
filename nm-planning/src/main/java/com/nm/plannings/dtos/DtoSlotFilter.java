package com.nm.plannings.dtos;

import java.util.Collection;
import java.util.Date;

/**
 * 
 * @author Nabil
 * 
 */
public class DtoSlotFilter {
	private Date from;
	private Date to;
	private Date existsFrom;
	private Date existsTo;
	private Collection<Number> ids;

	public Collection<Number> getIds() {
		return ids;
	}

	public DtoSlotFilter setIds(Collection<Number> ids) {
		this.ids = ids;
		return this;
	}

	public Date getExistsFrom() {
		return existsFrom;
	}

	public Date getExistsTo() {
		return existsTo;
	}

	public void setExistsFrom(Date existsFrom) {
		this.existsFrom = existsFrom;
	}

	public void setExistsTo(Date existsTo) {
		this.existsTo = existsTo;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

}
