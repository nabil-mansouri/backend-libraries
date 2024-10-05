package com.nm.comms.models;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nm.comms.constants.MessagePartType;
import com.nm.utils.hibernate.impl.ModelTimeable;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_communication_message")
public class Message extends ModelTimeable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "seq_communication_message", sequenceName = "seq_communication_message", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_communication_message")
	private Long id;
	//
	@ManyToOne(optional = false)
	private CommunicationActor sender;
	@ManyToMany()
	private Collection<CommunicationActor> receivers = Sets.newHashSet();
	@ManyToMany()
	private Collection<CommunicationMedium> mediums = Sets.newHashSet();
	//
	@Size(min = 1)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "nm_communication_message_contents")
	private Collection<MessageContent> contents = Sets.newHashSet();
	@OneToOne(cascade = CascadeType.ALL)
	private MessageTrigger trigger;
	@ManyToOne(optional = false)
	@Fetch(FetchMode.JOIN)
	private Communication communication;

	public Message() {
		setTrigger(new MessageTrigger());
	}

	public Communication getCommunication() {
		return communication;
	}

	public void setCommunication(Communication communication) {
		this.communication = communication;
	}

	public MessageTrigger getTrigger() {
		return trigger;
	}

	public void setTrigger(MessageTrigger time) {
		this.trigger = time;
		this.trigger.setMessage(this);
	}

	public Collection<CommunicationMedium> getMediums() {
		return mediums;
	}

	public void setMediums(Collection<CommunicationMedium> mediums) {
		this.mediums = mediums;
	}

	public Long getId() {
		return id;
	}

	public CommunicationActor getSender() {
		return sender;
	}

	public void setSender(CommunicationActor sender) {
		this.sender = sender;
	}

	public Collection<CommunicationActor> getReceivers() {
		return receivers;
	}

	public void setReceivers(Collection<CommunicationActor> receivers) {
		this.receivers = receivers;
	}

	public Collection<MessageContent> getContents() {
		return contents;
	}

	public Collection<MessageContent> findAll(MessagePartType type) {
		Collection<MessageContent> c = Lists.newArrayList();
		for (MessageContent m : contents) {
			if (m.getType().equals(type)) {
				c.add(m);
			}
		}
		return c;
	}

	public MessageContent findFirst(MessagePartType type) {
		for (MessageContent m : contents) {
			if (m.getType().equals(type)) {
				return m;
			}
		}
		return null;
	}

	public void setContents(Collection<MessageContent> contents) {
		this.contents = contents;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
