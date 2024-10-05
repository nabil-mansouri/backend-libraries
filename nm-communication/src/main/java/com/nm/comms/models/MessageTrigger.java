package com.nm.comms.models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.nm.app.triggers.TriggerSubject;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_communication_trigger_subject")
public class MessageTrigger extends TriggerSubject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@OneToOne(mappedBy = "trigger")
	private Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
}
