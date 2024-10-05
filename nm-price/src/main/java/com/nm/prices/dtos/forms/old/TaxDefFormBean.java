package com.nm.prices.dtos.forms.old;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.prices.dtos.constants.TaxeApplicability;
import com.nm.prices.dtos.constants.TaxeEvents;
import com.nm.prices.dtos.constants.TaxeType;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxDefFormBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private TaxeType type;
	private double nominateur;
	private double denominateur = 1;
	private List<TaxeApplicability> applicabilities = new ArrayList<TaxeApplicability>();
	private Map<TaxeEvents, Boolean> events = new HashMap<TaxeEvents, Boolean>();
	private ComputeDetailBean computed = new ComputeDetailBean();

	public ComputeDetailBean getComputed() {
		return computed;
	}

	public void setComputed(ComputeDetailBean computed) {
		this.computed = computed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<TaxeEvents, Boolean> getEvents() {
		return events;
	}

	public void setEvents(Map<TaxeEvents, Boolean> events) {
		this.events = events;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TaxeType getType() {
		return type;
	}

	public void setType(TaxeType type) {
		this.type = type;
	}

	public double getNominateur() {
		return nominateur;
	}

	public void setNominateur(double nominateur) {
		this.nominateur = nominateur;
	}

	public double getDenominateur() {
		return denominateur;
	}

	public void setDenominateur(double denominateur) {
		this.denominateur = denominateur;
	}

	public List<TaxeApplicability> getApplicabilities() {
		return applicabilities;
	}

	public void setApplicabilities(List<TaxeApplicability> applicabilities) {
		this.applicabilities = applicabilities;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
