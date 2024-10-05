package com.nm.comms.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_communication_subject_simple")
public class CommunicationSubjectSimple extends CommunicationSubject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@OneToOne(cascade = CascadeType.ALL)
	private MessageContent content;

	public MessageContent getContent() {
		return content;
	}

	public CommunicationSubjectSimple setContent(MessageContent content) {
		this.content = content;
		return this;
	}

}
