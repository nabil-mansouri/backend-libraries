package com.rm.model.clients.criterias;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.rm.contract.clients.constants.ClientEventType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_client_criterias_rule_event")
public class CriteriaRuleEvent extends CriteriaRule {

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CriteriaRuleEvent() {
	}

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ClientEventType type;

	public ClientEventType getType() {
		return type;
	}

	public void setType(ClientEventType type) {
		this.type = type;
	}
}
