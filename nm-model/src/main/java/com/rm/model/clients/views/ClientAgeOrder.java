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

import com.rm.contract.orders.constants.OrderStateType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity()
@Immutable()
@Table(name = "nm_view_order_age")
public class ClientAgeOrder implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "client_id")
	private Long idClient;
	@Column(name = "duration")
	private Double duration;
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private OrderStateType type;
	@Column(name = "last_date")
	private Date lastDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdClient() {
		return idClient;
	}

	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public OrderStateType getType() {
		return type;
	}

	public void setType(OrderStateType type) {
		this.type = type;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

}
