package com.rm.model.discounts.rules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.rm.model.discounts.rules.conditions.DiscountRuleCondition;
import com.rm.model.discounts.rules.subject.DiscountRuleSubject;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_discount_rule")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class DiscountRule implements Serializable {

	protected static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_discount_rule", sequenceName = "seq_discount_rule", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_discount_rule")
	protected Long id;
	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	protected Date updated = new Date();
	@ManyToOne(optional = false)
	private DiscountRules rules;
	//
	@OneToOne(optional = false)
	private DiscountRuleSubject subject;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "rule")
	private Collection<DiscountRuleCondition> conditions = new ArrayList<DiscountRuleCondition>();

	public Collection<DiscountRuleCondition> getConditions() {
		return conditions;
	}

	public void setConditions(Collection<DiscountRuleCondition> conditions) {
		this.conditions = conditions;
	}

	public DiscountRuleSubject getSubject() {
		return subject;
	}

	public void setSubject(DiscountRuleSubject subject) {
		this.subject = subject;
	}

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

	public DiscountRules getRules() {
		return rules;
	}

	public void setRules(DiscountRules rule) {
		this.rules = rule;
	}

}
