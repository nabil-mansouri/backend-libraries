package com.nm.plannings;

import java.io.Serializable;

import org.springframework.context.ApplicationEvent;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.plannings.constants.PlanningType;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanningeableDeleteEvent extends ApplicationEvent implements Serializable {

	public PlanningeableDeleteEvent(Object source) {
		super(source);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idPlanningeable;
	private PlanningType type;

	public Long getIdPlanningeable() {
		return idPlanningeable;
	}

	public PlanningeableDeleteEvent setIdPlanningeable(Long idPlanningeable) {
		this.idPlanningeable = idPlanningeable;
		return this;
	}

	public PlanningType getType() {
		return type;
	}

	public PlanningeableDeleteEvent setType(PlanningType type) {
		this.type = type;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
