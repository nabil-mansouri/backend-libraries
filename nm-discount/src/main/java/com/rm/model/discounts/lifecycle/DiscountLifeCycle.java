package com.rm.model.discounts.lifecycle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.rm.contract.discounts.constants.DiscountLifeCycleStateType;
import com.rm.model.discounts.DiscountDefinition;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_discount_lifecycle")
public class DiscountLifeCycle implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_discount_lifecycle", sequenceName = "seq_discount_lifecycle", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_discount_lifecycle")
	protected Long id;
	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	protected Date updated = new Date();
	//
	@ManyToOne()
	private DiscountDefinition discount;
	// STATE
	private boolean disabled;

	@OneToOne(optional = true)
	private DiscountLifeCycleState lastState;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "lifecycle")
	private List<DiscountLifeCycleState> states = new ArrayList<DiscountLifeCycleState>();

	//
	public DiscountLifeCycle() {
		add(new DiscountLifeCycleState(DiscountLifeCycleStateType.Planned));
	}

	public DiscountLifeCycle(DiscountDefinition discount) {
		this();
		this.setDiscount(discount);
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public void add(DiscountLifeCycleState state) {
		setLastState(state);
		state.setLifecycle(this);
		states.add(state);
	}

	public List<DiscountLifeCycleState> getStates() {
		return states;
	}

	public void setStates(List<DiscountLifeCycleState> states) {
		this.states = states;
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

	public DiscountLifeCycleState getLastState() {
		return lastState;
	}

	public void setLastState(DiscountLifeCycleState lastState) {
		this.lastState = lastState;
	}

	public DiscountDefinition getDiscount() {
		return discount;
	}

	public void setDiscount(DiscountDefinition discount) {
		this.discount = discount;
	}

}
