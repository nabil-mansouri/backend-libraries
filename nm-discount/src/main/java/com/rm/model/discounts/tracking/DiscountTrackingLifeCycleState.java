package com.rm.model.discounts.tracking;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.rm.contract.discounts.constants.DiscountLifeCycleStateType;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_discount_tracking_lifecycle_state")
public class DiscountTrackingLifeCycleState implements Serializable {

	private static final long serialVersionUID = 1978550118107172866L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_discount_tracking_lifecycle_state", sequenceName = "seq_discount_tracking_lifecycle_state", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_discount_tracking_lifecycle_state")
	protected Long id;
	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DiscountLifeCycleStateType type;
	@ManyToOne(optional = false)
	private DiscountTrackingLifeCycle lifecycle;
	//
	@Formula("DATE_PART('epoch', NOW() - created)")
	private Double ageInSeconds;

	public Double getAgeInSeconds() {
		return ageInSeconds;
	}

	public void setAgeInSeconds(Double ageInSeconds) {
		this.ageInSeconds = ageInSeconds;
	}

	public DiscountTrackingLifeCycle getLifecycle() {
		return lifecycle;
	}

	public void setLifecycle(DiscountTrackingLifeCycle planning) {
		this.lifecycle = planning;
	}

	public DiscountTrackingLifeCycleState() {
	}

	public DiscountTrackingLifeCycleState(DiscountLifeCycleStateType type) {
		super();
		this.type = type;
	}

	public DiscountTrackingLifeCycleState(Date created, DiscountLifeCycleStateType type) {
		super();
		this.created = created;
		this.type = type;
	}

	public DiscountTrackingLifeCycleState(DiscountLifeCycleStateType type, DiscountTrackingLifeCycle planning) {
		super();
		this.type = type;
		this.setLifecycle(planning);
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

	public DiscountLifeCycleStateType getType() {
		return type;
	}

	public void setType(DiscountLifeCycleStateType type) {
		this.type = type;
	}

}
