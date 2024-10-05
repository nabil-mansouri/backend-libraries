package com.rm.model.clients;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rm.contract.clients.constants.ClientActionType;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_client_action")
public class ClientActions implements Serializable {

	private static final long serialVersionUID = 1978550118107172866L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_client_action", sequenceName = "seq_client_action", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_client_action")
	protected Long id;
	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	@Enumerated(EnumType.STRING)
	@NotNull
	private ClientActionType type;
	@ManyToOne(optional = false)
	private Client client;

	public ClientActions() {
	}

	public ClientActions(ClientActionType type) {
		super();
		this.type = type;
	}

	public ClientActions(ClientActionType type, Client client) {
		super();
		this.type = type;
		this.client = client;
	}

	public ClientActions(Date date, ClientActionType logged) {
		this.type = logged;
		this.created = date;
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

	public ClientActionType getType() {
		return type;
	}

	public void setType(ClientActionType type) {
		this.type = type;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
