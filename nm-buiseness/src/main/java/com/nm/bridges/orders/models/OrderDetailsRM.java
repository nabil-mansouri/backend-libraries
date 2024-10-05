package com.nm.bridges.orders.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.nm.bridges.prices.OrderType;
import com.nm.orders.models.OrderDetails;
import com.nm.products.model.ProductInstance;
import com.nm.shop.model.Shop;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_order_details_rm")
public class OrderDetailsRM extends OrderDetails {

	protected static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// TODO
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Size(min = 1, message = "order.products.empty")
	private List<ProductInstance> details = new ArrayList<ProductInstance>();
	@ManyToOne(optional = false)
	private Shop shop;
	@Enumerated(EnumType.STRING)
	@NotNull
	private OrderType orderType;
}
