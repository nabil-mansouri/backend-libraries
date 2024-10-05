package com.rm.model.discounts.rules.subject;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.AssertFalse;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_discount_rule_subject_additionnal")
public class DiscountRuleSubjectAdditionnal extends DiscountRuleSubject {

	protected static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "add")
	private Collection<DiscountRuleSubjectAdditionnalNode> additionnal = new ArrayList<DiscountRuleSubjectAdditionnalNode>();
	@Column(nullable = false, name = "select_all")
	private boolean all;

	public boolean isAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}

	@AssertFalse
	protected boolean assertTrue() {
		return all && additionnal.size() > 1;
	}

	//
	public Collection<DiscountRuleSubjectAdditionnalNode> getAdditionnal() {
		return additionnal;
	}

	public void setAdditionnal(Collection<DiscountRuleSubjectAdditionnalNode> additionnal) {
		this.additionnal = additionnal;
	}

}
