package com.nm.bridges.orders.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.nm.orders.models.OrderAccount;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_order_account_client")
public class OrderAccountClient extends OrderAccount {

	protected static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	// TODO
	// @OneToOne(optional = true)
	// private Client client;
	//
	// public Client getClient() {
	// return client;
	// }
	//
	// public void setClient(Client client) {
	// this.client = client;
	// }
}
