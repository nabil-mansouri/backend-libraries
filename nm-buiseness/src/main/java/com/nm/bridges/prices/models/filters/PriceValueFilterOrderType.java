package com.nm.bridges.prices.models.filters;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.nm.bridges.prices.OrderType;
import com.nm.prices.model.filter.PriceValueFilter;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_price_value_filter_order_type")
public class PriceValueFilterOrderType extends PriceValueFilter implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	protected OrderType orderType;

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
}
