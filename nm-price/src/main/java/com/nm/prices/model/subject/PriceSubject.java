package com.nm.prices.model.subject;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_price_subject")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PriceSubject implements Serializable {

	protected static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_rm_price_subject", sequenceName = "seq_rm_price_subject", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_rm_price_subject")
	protected Long id;
	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	protected Date updated = new Date();
	@Column(nullable = false)
	private Boolean root = false;

	public Boolean getRoot() {
		return root;
	}

	public void setRoot(Boolean root) {
		this.root = root;
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
