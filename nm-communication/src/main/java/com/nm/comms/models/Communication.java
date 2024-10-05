package com.nm.comms.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.nm.utils.hibernate.impl.ModelTimeable;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_communication_channel")
public class Communication extends ModelTimeable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_communication_channel", sequenceName = "seq_communication_channel", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_communication_channel")
	private Long id;
	//
	@ManyToOne(optional = false)
	private CommunicationSubject about;
	@ManyToOne(optional = false)
	private CommunicationActor owner;
	//
	@OneToMany(mappedBy = "communication")
	private List<Message> messages = new ArrayList<Message>();
	@OneToOne(optional = true)
	private Message last;

	public void add(Message comm) {
		this.setLast(comm);
		comm.setCommunication(this);
		this.messages.add(comm);
	}

	public CommunicationActor getOwner() {
		return owner;
	}

	public void setOwner(CommunicationActor owner) {
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CommunicationSubject getAbout() {
		return about;
	}

	public void setAbout(CommunicationSubject about) {
		this.about = about;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> communications) {
		this.messages = communications;
	}

	public Message getLast() {
		return last;
	}

	public void setLast(Message last) {
		this.last = last;
	}

}
