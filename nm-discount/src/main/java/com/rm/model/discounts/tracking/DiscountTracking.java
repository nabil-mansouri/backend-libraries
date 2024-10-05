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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.rm.model.discounts.DiscountDefinition;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_discount_tracking")
public class DiscountTracking implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_discount_tracking", sequenceName = "seq_discount_tracking", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_discount_tracking")
	protected Long id;
	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	protected Date updated = new Date();
	//
	@Column(name = "client_id", insertable = false, updatable = false)
	private Long clientId;
	@ManyToOne(optional = false)
	@JoinColumn(name = "client_id")
	private Client client;
	/**
	 * 
	 */
	@Column(name = "discount_id", insertable = false, updatable = false)
	private Long discountId;
	@ManyToOne(optional = false)
	@JoinColumn(name = "discount_id")
	private DiscountDefinition discount;
	/**
	 * Lifecycle
	 */
	@OneToOne(optional = true, cascade = CascadeType.ALL)
	private DiscountTrackingLifeCycle lifecycle;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "tracking")
	private List<DiscountTrackingLifeCycle> history = new ArrayList<DiscountTrackingLifeCycle>();

	//
	public DiscountTracking() {
		pushLifeCycle();
	}

	public List<DiscountTrackingLifeCycle> getHistory() {
		return history;
	}

	public void setHistory(List<DiscountTrackingLifeCycle> history) {
		this.history = history;
	}

	public void pushLifeCycle() {
		this.lifecycle = new DiscountTrackingLifeCycle(this);
		this.history.add(this.lifecycle);
	}

	public DiscountTrackingLifeCycle getLifecycle() {
		return lifecycle;
	}

	public Long getClientId() {
		return clientId;
	}

	public Long getDiscountId() {
		return discountId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

	public void setLifecycle(DiscountTrackingLifeCycle lifecycle) {
		this.lifecycle = lifecycle;
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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public DiscountDefinition getDiscount() {
		return discount;
	}

	public void setDiscount(DiscountDefinition discount) {
		this.discount = discount;
	}

}
