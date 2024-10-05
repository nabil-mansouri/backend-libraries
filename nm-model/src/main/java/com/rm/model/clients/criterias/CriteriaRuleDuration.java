package com.rm.model.clients.criterias;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;

import com.rm.utils.dates.PeriodType;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_client_criterias_rule_duration")
public class CriteriaRuleDuration extends CriteriaRule {

	private static final long serialVersionUID = 1L;
	@Column(name = "from_duration")
	private Double from;
	@Column(name = "to_duration")
	private Double to;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PeriodType original;

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

	public PeriodType getOriginal() {
		return original;
	}

	public void setOriginal(PeriodType original) {
		this.original = original;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@AssertTrue
	protected boolean assertRange() {
		return from != null || to != null;
	}
}
