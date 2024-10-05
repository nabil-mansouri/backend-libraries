package com.rm.model.discounts.tracking.views;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.rm.model.discounts.DiscountDefinition;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity()
@Immutable()
@Table(name = "nm_view_discount_tracking_stats")
public class DiscountTrackingStatView implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	//
	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "created")
	private Date created;
	@Column(name = "client_id", insertable = false, updatable = false)
	private Long idClient;
	@Column(name = "discount_id", insertable = false, updatable = false)
	private Long idDiscount;
	@Column(name = "nb_used")
	private Long nbUsed;
	@ManyToOne()
	@JoinColumn(name = "client_id")
	private Client client;
	@ManyToOne()
	@JoinColumn(name = "discount_id")
	private DiscountDefinition discount;

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

	public Long getIdClient() {
		return idClient;
	}

	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}

	public Long getIdDiscount() {
		return idDiscount;
	}

	public void setIdDiscount(Long idDiscount) {
		this.idDiscount = idDiscount;
	}

	public Long getNbUsed() {
		return nbUsed;
	}

	public void setNbUsed(Long nbUsed) {
		this.nbUsed = nbUsed;
	}

}
