package com.nm.plannings.dtos;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.plannings.constants.SlotRepeatKind;
import com.nm.plannings.constants.PlanningDays;
import com.nm.plannings.constants.SlotType;
import com.nm.utils.dtos.Dto;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoTimeSlot implements Dto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Long id;
	private SlotRepeatKind type;
	private Date dateBeginPlan;
	private Date dateEndPlan;
	private Date dateBeginHoraire;
	private Date dateEndHoraire;
	// TODO no limit begi, and end
	private boolean hasNoLimit = false;
	private SlotType typeOfSlot;
	private String cause;
	private List<PlanningDays> planningDays = new ArrayList<PlanningDays>();
	// VIEW
	private BigInteger aboutId;

	public DtoTimeSlot() {
	}

	public DtoTimeSlot(Long id) {
		super();
		this.id = id;
	}

	public BigInteger getAboutId() {
		return aboutId;
	}

	public void setAboutId(BigInteger aboutId) {
		this.aboutId = aboutId;
	}

	public Long getId() {
		return id;
	}

	public DtoTimeSlot setId(Long id) {
		this.id = id;
		return this;
	}

	public String getCause() {
		return cause;
	}

	public Date getDateBeginHoraire() {
		return dateBeginHoraire;
	}

	public Date getDateBeginPlan() {
		return dateBeginPlan;
	}

	public Date getDateEndHoraire() {
		return dateEndHoraire;
	}

	public Date getDateEndPlan() {
		return dateEndPlan;
	}

	public List<PlanningDays> getPlanningDays() {
		return planningDays;
	}

	public SlotRepeatKind getType() {
		return type;
	}

	public SlotType getTypeOfSlot() {
		return typeOfSlot;
	}

	public boolean isHasNoLimit() {
		return hasNoLimit;
	}

	public DtoTimeSlot setCause(String cause) {
		this.cause = cause;
		return this;
	}

	public DtoTimeSlot setDateBeginHoraire(Date dateBeginHoraire) {
		this.dateBeginHoraire = dateBeginHoraire;
		return this;
	}

	public DtoTimeSlot setDateBeginPlan(Date dateBeginPlan) {
		this.dateBeginPlan = dateBeginPlan;
		return this;
	}

	public DtoTimeSlot setDateEndHoraire(Date dateEndHoraire) {
		this.dateEndHoraire = dateEndHoraire;
		return this;
	}

	public DtoTimeSlot setDateEndPlan(Date dateEndPlan) {
		this.dateEndPlan = dateEndPlan;
		return this;
	}

	public DtoTimeSlot setHasNoLimit(boolean hasNoEndPlan) {
		this.hasNoLimit = hasNoEndPlan;
		return this;
	}

	public DtoTimeSlot setPlanningDays(List<PlanningDays> planningDays) {
		this.planningDays = planningDays;
		return this;
	}

	public DtoTimeSlot withPlanningDays(PlanningDays planningDays) {
		this.planningDays.add(planningDays);
		return this;
	}

	public DtoTimeSlot addPlanningDays(PlanningDays planningDay) {
		this.planningDays.add(planningDay);
		return this;
	}

	public DtoTimeSlot setType(SlotRepeatKind type) {
		this.type = type;
		return this;
	}

	public DtoTimeSlot setTypeOfSlot(SlotType typeOfSlot) {
		this.typeOfSlot = typeOfSlot;
		return this;
	}

}
