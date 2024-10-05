package com.rm.soa.events;

import org.springframework.context.ApplicationEvent;

import com.rm.model.orders.Order;

/**
 * 
 * @author Nabil
 * 
 */
public class OnOrderTransactionChanged extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Order order;

	public OnOrderTransactionChanged(Object source, Order order) {
		super(source);
		this.order = order;
	}

	public Order getOrder() {
		return order;
	}

}
