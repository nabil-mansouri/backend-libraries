package com.nm.plannings.model;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;

import org.joda.time.MutableDateTime;

import com.nm.plannings.constants.PlanningDays;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_planning_timeslot_recurrent", schema = "mod_planning")
public class TimeSlotRecurrent extends TimeSlot {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PlanningDays days;

	@Column(nullable = false)
	private Time beginHoraire;

	@Column(nullable = false)
	private Time endHoraire;
	@Column(nullable = false)
	private boolean noEndPlan = false;

	public PlanningDays getDays() {
		return days;
	}

	public TimeSlotRecurrent setDays(PlanningDays beginPlan) {
		this.days = beginPlan;
		return this;
	}

	public Time getBeginHoraire() {
		return beginHoraire;
	}

	public TimeSlotRecurrent setBeginHoraire(Time beginHoraire) {
		this.beginHoraire = beginHoraire;
		return this;
	}

	public TimeSlotRecurrent setBeginHoraire(Date beginHoraire) {
		this.beginHoraire = new Time(beginHoraire.getTime());
		return this;
	}

	public Time getEndHoraire() {
		return endHoraire;
	}

	public boolean isNoEndPlan() {
		return noEndPlan;
	}

	public TimeSlotRecurrent setNoEndPlan(boolean noEndPlan) {
		this.noEndPlan = noEndPlan;
		return this;
	}

	public TimeSlotRecurrent setEndHoraire(Time endHoraire) {
		this.endHoraire = endHoraire;
		return this;
	}

	public TimeSlotRecurrent setEndHoraire(Date endHoraire) {
		this.endHoraire = new Time(endHoraire.getTime());
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@AssertTrue(message = "slot.horaire.range")
	protected boolean isValidHoraireRange() {
		if (beginHoraire == null || endHoraire == null) {
			return false;
		}
		Long begin = beginHoraire.getTime();
		Long end = endHoraire.getTime();
		return begin <= end;
	}

	@Override
	protected boolean isValidPlanRange() {
		if (this.noEndPlan) {
			return true;
		} else {
			return super.isValidPlanRange();
		}
	}

	public TimeSlotRecurrent withNoEndPlan(boolean noEndPlan) {
		setNoEndPlan(noEndPlan);
		// INIFINITE DATE
		if (noEndPlan) {
			MutableDateTime mutableEnd = new MutableDateTime(getEndHoraire());
			mutableEnd.addYears(200);
			setEndPlan(mutableEnd.toDate());
		}
		return this;
	}

	@Override
	public TimeSlot clone() {
		TimeSlotRecurrent slot = new TimeSlotRecurrent();
		slot.setBeginHoraire(getBeginHoraire());
		slot.setBeginPlan(getBeginPlan());
		slot.setDays(getDays());
		slot.setEndHoraire(getEndHoraire());
		slot.setEndPlan(getEndPlan());
		slot.setNoEndPlan(isNoEndPlan());
		slot.setPlanning(getPlanning());
		slot.setType(getType());
		return slot;
	}
}
