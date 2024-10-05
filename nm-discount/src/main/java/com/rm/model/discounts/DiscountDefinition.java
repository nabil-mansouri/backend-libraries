package com.rm.model.discounts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.rm.contract.discounts.constants.DiscountLifeCycleRuleType;
import com.rm.contract.discounts.constants.DiscountTrackingLifeCycleRuleType;
import com.rm.model.discounts.communication.DiscountCommunication;
import com.rm.model.discounts.lifecycle.DiscountLifeCycle;
import com.rm.model.discounts.lifecycle.DiscountLifeCycleRule;
import com.rm.model.discounts.rules.DiscountRules;
import com.rm.model.discounts.tracking.DiscountTrackingLifeCycleRule;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_discount_definition")
public class DiscountDefinition implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_discount_definition", sequenceName = "seq_discount_definition", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_discount_definition")
	protected Long id;

	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	protected Date updated = new Date();
	@Column(nullable = false)
	protected String name;
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	protected DiscountRules rule = new DiscountRules();
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	protected DiscountCommunication communication = new DiscountCommunication();
	//
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	private DiscountLifeCycle lifecycle;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "discount")
	private List<DiscountLifeCycle> history = new ArrayList<DiscountLifeCycle>();
	//
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	private ClientCriterias clientCriterias = new ClientCriterias();
	//
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "discount")
	@MapKey(name = "type")
	private Map<DiscountLifeCycleRuleType, DiscountLifeCycleRule> lifeRules = new HashMap<DiscountLifeCycleRuleType, DiscountLifeCycleRule>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "discount")
	@MapKey(name = "type")
	private Map<DiscountTrackingLifeCycleRuleType, DiscountTrackingLifeCycleRule> trackingRules = new HashMap<DiscountTrackingLifeCycleRuleType, DiscountTrackingLifeCycleRule>();

	public void pushLifeCycle() {
		this.lifecycle = new DiscountLifeCycle(this);
		this.history.add(this.lifecycle);
	}

	public void put(DiscountTrackingLifeCycleRuleType type, DiscountTrackingLifeCycleRule rule) {
		rule.setType(type);
		rule.setDiscount(this);
		this.trackingRules.put(type, rule);
	}

	public void put(DiscountLifeCycleRuleType type, DiscountLifeCycleRule rule) {
		rule.setType(type);
		rule.setDiscount(this);
		this.lifeRules.put(type, rule);
	}

	public Map<DiscountLifeCycleRuleType, DiscountLifeCycleRule> getLifeRules() {
		return lifeRules;
	}

	public void setLifeRules(Map<DiscountLifeCycleRuleType, DiscountLifeCycleRule> lifeRules) {
		this.lifeRules = lifeRules;
	}

	public void clearTrackingRules() {
		this.trackingRules.clear();
	}

	public void clearLifeRules() {
		this.lifeRules.clear();
	}

	public DiscountDefinition() {
		this.pushLifeCycle();
	}

	public DiscountLifeCycle getLifecycle() {
		return lifecycle;
	}

	public void setLifecycle(DiscountLifeCycle lifecycle) {
		this.lifecycle = lifecycle;
	}

	public ClientCriterias getClientCriterias() {
		return clientCriterias;
	}

	public void setClientCriterias(ClientCriterias clientCriterias) {
		this.clientCriterias = clientCriterias;
	}

	public Date getUpdated() {
		return updated;
	}

	public DiscountRules getRule() {
		return rule;
	}

	public void setRule(DiscountRules rule) {
		this.rule = rule;
	}

	public DiscountCommunication getCommunication() {
		return communication;
	}

	public void setCommunication(DiscountCommunication communication) {
		this.communication = communication;
	}

	public List<DiscountLifeCycle> getHistory() {
		return history;
	}

	public void setHistory(List<DiscountLifeCycle> history) {
		this.history = history;
	}

	public Map<DiscountTrackingLifeCycleRuleType, DiscountTrackingLifeCycleRule> getTrackingRules() {
		return trackingRules;
	}

	public void setTrackingRules(Map<DiscountTrackingLifeCycleRuleType, DiscountTrackingLifeCycleRule> trackingRules) {
		this.trackingRules = trackingRules;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreated() {
		return created;
	}

	public Long getId() {
		return id;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
