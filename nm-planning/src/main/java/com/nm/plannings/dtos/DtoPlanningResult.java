package com.nm.plannings.dtos;

import java.util.Collection;

import com.google.common.collect.Lists;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class DtoPlanningResult {

	private boolean assertion;
	private Collection<DtoSlotOccurrence> events = Lists.newArrayList();

	public boolean isAssertion() {
		return assertion;
	}

	public void setAssertion(boolean assertion) {
		this.assertion = assertion;
	}

	public Collection<DtoSlotOccurrence> getEvents() {
		return events;
	}

	public void setEvents(Collection<DtoSlotOccurrence> events) {
		this.events = events;
	}

}
