package com.nm.carts.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_cart_owner_session")
public class CartOwnerSession extends CartOwner {

	protected static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(nullable = false)
	private String session;

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}
}
