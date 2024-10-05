package com.rm.model.clients.criterias;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.rm.contract.clients.constants.ClientCriteriaType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_client_criterias_rules")
public class CriteriaRules {

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_client_criterias_rules", sequenceName = "seq_client_criterias_rules", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_client_criterias_rules")
	protected Long id;
	@ManyToOne(optional = false)
	private ClientCriterias criterias;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ClientCriteriaType type;
	@OneToMany(cascade = CascadeType.ALL)
	private List<CriteriaRule> rules = new ArrayList<CriteriaRule>();

	public CriteriaRules() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ClientCriterias getCriterias() {
		return criterias;
	}

	public void setCriterias(ClientCriterias criterias) {
		this.criterias = criterias;
	}

	public ClientCriteriaType getType() {
		return type;
	}

	public void setType(ClientCriteriaType type) {
		this.type = type;
	}

	public List<CriteriaRule> getRules() {
		return rules;
	}

	public void setRules(List<CriteriaRule> rules) {
		this.rules = rules;
	}

	public void add(CriteriaRule rule) {
		this.rules.add(rule);
	}
}
