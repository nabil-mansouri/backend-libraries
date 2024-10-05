package com.rm.model.clients.views;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.rm.contract.clients.constants.ClientActionType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity()
@Immutable()
@Table(name = "nm_view_client_age_action")
public class ClientAgeAction implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@Column(name = "action_id")
	private Long idAction;
	@Column(name = "client_id")
	private Long idClient;
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private ClientActionType type;
	@Column(name = "action_date")
	private Date actionDate;
	@Column(name = "duration")
	private Double duration;

	public Long getIdAction() {
		return idAction;
	}

	public void setIdAction(Long idAction) {
		this.idAction = idAction;
	}

	public Long getIdClient() {
		return idClient;
	}

	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}

	public ClientActionType getType() {
		return type;
	}

	public void setType(ClientActionType type) {
		this.type = type;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

}
