package com.nm.plannings.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_planning_timeslot_exceptionnal", schema = "mod_planning")
public class TimeSlotExceptionnal extends TimeSlot {

	private static final long serialVersionUID = 1L;

	private String cause;

	public String getCause() {
		return cause;
	}

	public TimeSlotExceptionnal setCause(String cause) {
		this.cause = cause;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public TimeSlot clone() {
		TimeSlotExceptionnal slot = new TimeSlotExceptionnal();
		slot.setBeginPlan(getBeginPlan());
		slot.setCause(getCause());
		slot.setEndPlan(getEndPlan());
		slot.setPlanning(getPlanning());
		slot.setType(getType());
		return slot;
	}
}
