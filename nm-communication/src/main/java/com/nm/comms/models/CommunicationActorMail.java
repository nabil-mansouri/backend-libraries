package com.nm.comms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_communication_actor_email")
public class CommunicationActorMail extends CommunicationActor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(nullable = false)
	private String email;

	public String getEmail() {
		return email;
	}

	public CommunicationActorMail setEmail(String email) {
		this.email = email;
		return this;
	}
}
