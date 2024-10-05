package com.rm.model.discounts.tracking;

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

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_discount_tracking_lifecycle")
public class DiscountTrackingLifeCycle implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_discount_tracking_lifecycle", sequenceName = "seq_discount_tracking_lifecycle", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_discount_tracking_lifecycle")
	protected Long id;
	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	protected Date updated = new Date();
	//
	@ManyToOne(optional = false)
	private DiscountTracking tracking;
	// STATE
	private boolean disabled;
	@OneToOne(optional = true)
	private DiscountTrackingLifeCycleState lastState;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "lifecycle")
	private List<DiscountTrackingLifeCycleState> states = new ArrayList<DiscountTrackingLifeCycleState>();

	//
	public DiscountTrackingLifeCycle() {
		this.add(new DiscountTrackingLifeCycleState(DiscountLifeCycleStateType.Planned));
	}

	public DiscountTrackingLifeCycle(DiscountTracking tracking) {
		this();
		this.setTracking(tracking);
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public void add(DiscountTrackingLifeCycleState state) {
		setLastState(state);
		state.setLifecycle(this);
		states.add(state);
	}

	public List<DiscountTrackingLifeCycleState> getStates() {
		return states;
	}

	public void setStates(List<DiscountTrackingLifeCycleState> states) {
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

	public DiscountTrackingLifeCycleState getLastState() {
		return lastState;
	}

	public void setLastState(DiscountTrackingLifeCycleState lastState) {
		this.lastState = lastState;
	}

	public DiscountTracking getTracking() {
		return tracking;
	}

	public void setTracking(DiscountTracking tracking) {
		this.tracking = tracking;
	}

}
