package com.nm.carts.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_cart_cart")
public class Cart implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_cart_cart", sequenceName = "seq_cart_cart", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_cart_cart")
	private Long id;
	@OneToOne(optional = false, orphanRemoval = true, cascade = CascadeType.ALL)
	private CartDetail detail = new CartDetail();
	@Column(nullable = false, updatable = false)
	private Date createdAt = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	private Date updatedAt = new Date();
	@ManyToOne(optional = false)
	private CartOwner owner;

	public CartOwner getOwner() {
		return owner;
	}

	public void setOwner(CartOwner owner) {
		this.owner = owner;
	}
 

	public Date getCreatedAt() {
		return createdAt;
	}

	public Long getId() {
		return id;
	}

	 
	public Date getUpdatedAt() {
		return updatedAt;
	}

	 

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(Long id) {
		this.id = id;
	}

	 

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", detail=" + detail + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", owner=" + owner + "]";
	}

	@PreUpdate
	public void onUpdate() {
		this.setUpdatedAt(new Date());
	}

	public CartDetail getDetail() {
		return detail;
	}

	public void setDetail(CartDetail detail) {
		this.detail = detail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
