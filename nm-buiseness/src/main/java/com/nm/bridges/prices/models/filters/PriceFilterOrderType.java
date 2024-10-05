package com.nm.bridges.prices.models.filters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.nm.bridges.prices.OrderType;
import com.nm.prices.model.filter.PriceFilter;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_price_filter_order_type")
public class PriceFilterOrderType extends PriceFilter implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "type")
	@ElementCollection(targetClass = OrderType.class)
	@Size(min = 1, message = "price.filter.noordertypes")
	@CollectionTable(name = "nm_price_filter_order_type_list", joinColumns = @JoinColumn(name = "id") )
	protected Collection<OrderType> orderType = new ArrayList<OrderType>();

	public Collection<OrderType> getOrderType() {
		return orderType;
	}

	public void setOrderType(Collection<OrderType> orderType) {
		this.orderType = orderType;
	}
}
