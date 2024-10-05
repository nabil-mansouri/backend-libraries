package com.rm.model.clients;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_client_sponsorship", uniqueConstraints = @UniqueConstraint(columnNames = { "sponsor_id", "child_id" }))
public class Sponsorship implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_client_sponsorship", sequenceName = "seq_client_sponsorship", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_client_sponsorship")
	protected Long id;
	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	@ManyToOne(optional = false)
	protected Client sponsor;
	@OneToOne(optional = false)
	protected Client child;

	public Sponsorship() {
	}

	public Sponsorship(Client sponsor, Client child) {
		super();
		this.sponsor = sponsor;
		this.child = child;
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

	public Client getSponsor() {
		return sponsor;
	}

	public void setSponsor(Client sponsor) {
		this.sponsor = sponsor;
	}

	public Client getChild() {
		return child;
	}

	public void setChild(Client child) {
		this.child = child;
	}

}
