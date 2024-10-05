package com.rm.model.clients.criterias;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_client_criterias_rule_void")
public class CriteriaRuleVoid extends CriteriaRule {

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CriteriaRuleVoid() {
	}

}
