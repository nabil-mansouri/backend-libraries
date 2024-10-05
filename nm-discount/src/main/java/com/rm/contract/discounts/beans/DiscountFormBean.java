package com.rm.contract.discounts.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rm.contract.discounts.constants.CommunicationType;
import com.rm.contract.discounts.constants.DiscountLifeCycleRuleType;
import com.rm.contract.discounts.constants.DiscountTrackingLifeCycleRuleType;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscountFormBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private List<DiscountRuleBean> rules = new ArrayList<DiscountRuleBean>();
	private Map<CommunicationType, DiscountCommunicationBean> communication = new HashMap<CommunicationType, DiscountCommunicationBean>();
	private String name;
	private Date created;
	private int nbSupplement;
	private Map<DiscountLifeCycleRuleType, DiscountLifeCycleRuleBean> lifeRules = new HashMap<DiscountLifeCycleRuleType, DiscountLifeCycleRuleBean>();
	private Map<DiscountTrackingLifeCycleRuleType, DiscountLifeCycleTrackingRuleBean> trackingLifeRules = new HashMap<DiscountTrackingLifeCycleRuleType, DiscountLifeCycleTrackingRuleBean>();

	public DiscountFormBean addCOmmunication(CommunicationType type, DiscountCommunicationBean rule) {
		this.communication.put(type, rule);
		return this;
	}

	public Map<DiscountLifeCycleRuleType, DiscountLifeCycleRuleBean> getLifeRules() {
		return lifeRules;
	}

	public DiscountFormBean addRule(DiscountRuleBean rule) {
		this.rules.add(rule);
		return this;
	}

	public DiscountFormBean setLifeRules(Map<DiscountLifeCycleRuleType, DiscountLifeCycleRuleBean> lifeRules) {
		this.lifeRules = lifeRules;
		return this;
	}

	public DiscountFormBean addLifeRules(DiscountLifeCycleRuleType type, DiscountLifeCycleRuleBean lifeRule) {
		this.lifeRules.put(type, lifeRule);
		return this;
	}

	public Map<DiscountTrackingLifeCycleRuleType, DiscountLifeCycleTrackingRuleBean> getTrackingLifeRules() {
		return trackingLifeRules;
	}

	public DiscountFormBean setTrackingLifeRules(Map<DiscountTrackingLifeCycleRuleType, DiscountLifeCycleTrackingRuleBean> trackingLifeRules) {
		this.trackingLifeRules = trackingLifeRules;
		return this;
	}

	public DiscountFormBean addTrackingLifeRules(DiscountTrackingLifeCycleRuleType type, DiscountLifeCycleTrackingRuleBean tracking) {
		this.trackingLifeRules.put(type, tracking);
		return this;
	}

	public int getNbSupplement() {
		return nbSupplement;
	}

	public DiscountFormBean incrementNbSupplement() {
		nbSupplement++;
		return this;
	}

	public DiscountFormBean setNbSupplement(int nbSupplement) {
		this.nbSupplement = nbSupplement;
		return this;
	}

	public Date getCreated() {
		return created;
	}

	public DiscountFormBean setCreated(Date created) {
		this.created = created;
		return this;
	}

	public String getName() {
		return name;
	}

	public DiscountFormBean setName(String name) {
		this.name = name;
		return this;
	}

	public Long getId() {
		return id;
	}

	public DiscountFormBean setId(Long id) {
		this.id = id;
		return this;
	}

	public List<DiscountRuleBean> getRules() {
		return rules;
	}

	public DiscountFormBean setRules(List<DiscountRuleBean> rules) {
		this.rules = rules;
		return this;
	}

	public Map<CommunicationType, DiscountCommunicationBean> getCommunication() {
		return communication;
	}

	public DiscountFormBean setCommunication(Map<CommunicationType, DiscountCommunicationBean> communication) {
		this.communication = communication;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
