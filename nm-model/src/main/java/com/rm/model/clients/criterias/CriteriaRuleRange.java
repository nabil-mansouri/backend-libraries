package com.rm.model.clients.criterias;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_client_criterias_rule_range")
public class CriteriaRuleRange extends CriteriaRule {

	private static final long serialVersionUID = 1L;
	@Column(name="range_from")
	private Double from;
	@Column(name="range_to")
	private Double to;

	public Double getFrom() {
		return from;
	}

	public void setFrom(Double from) {
		this.from = from;
	}

	public Double getTo() {
		return to;
	}

	public void setTo(Double to) {
		this.to = to;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@AssertTrue
	protected boolean assertRange() {
		return from != null || to != null;
	}

}
