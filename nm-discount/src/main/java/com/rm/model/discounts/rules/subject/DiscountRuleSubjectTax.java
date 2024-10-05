package com.rm.model.discounts.rules.subject;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.AssertFalse;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_discount_rule_subject_tax")
public class DiscountRuleSubjectTax extends DiscountRuleSubject {

	protected static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@ManyToMany
	@JoinTable(name = "nm_discount_rule_subject_tax_join")
	private Collection<TaxDefinition> taxs = new HashSet<TaxDefinition>();
	@Column(nullable = false, name = "select_all")
	private boolean all;

	public Collection<TaxDefinition> getTaxs() {
		return taxs;
	}

	public void setTaxs(Collection<TaxDefinition> taxs) {
		this.taxs = taxs;
	}

	public boolean isAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}

	@AssertFalse
	protected boolean assertTrue() {
		return all && taxs.size() > 1;
	}
}
