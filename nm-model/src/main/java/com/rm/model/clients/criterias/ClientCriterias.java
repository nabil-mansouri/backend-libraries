package com.rm.model.clients.criterias;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.rm.contract.clients.constants.ClientCriteriaType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_client_criterias")
public class ClientCriterias implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_client_criterias", sequenceName = "seq_client_criterias", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_client_criterias")
	protected Long id;
	/**
	 * Dates
	 */
	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	protected Date updated = new Date();
	/**
	 * 
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "criterias")
	@MapKey(name = "type")
	private Map<ClientCriteriaType, CriteriaRules> rules = new HashMap<ClientCriteriaType, CriteriaRules>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Map<ClientCriteriaType, CriteriaRules> getRules() {
		return rules;
	}

	public void setRules(Map<ClientCriteriaType, CriteriaRules> rules) {
		this.rules = rules;
	}
	
	public void put(ClientCriteriaType type,CriteriaRules rules) {
		rules.setCriterias(this);
		rules.setType(type);
		this.rules.put(type, rules);
	}

}
