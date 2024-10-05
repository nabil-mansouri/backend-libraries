package com.rm.model.discounts.lifecycle;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.rm.contract.discounts.constants.DiscountLifeCycleRuleType;
import com.rm.model.discounts.DiscountDefinition;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_discount_lifecycle_rule")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class DiscountLifeCycleRule implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_discount_lifecycle_rule", sequenceName = "seq_discount_lifecycle_rule", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_discount_lifecycle_rule")
	protected Long id;
	/**
	 * Dates
	 */
	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	protected Date updated = new Date();
	//
	@Enumerated(EnumType.STRING)
	private DiscountLifeCycleRuleType type;
	@ManyToOne(optional = false)
	private DiscountDefinition discount;

	public DiscountDefinition getDiscount() {
		return discount;
	}

	public void setDiscount(DiscountDefinition discount) {
		this.discount = discount;
	}

	public DiscountLifeCycleRuleType getType() {
		return type;
	}

	public void setType(DiscountLifeCycleRuleType type) {
		this.type = type;
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

}
