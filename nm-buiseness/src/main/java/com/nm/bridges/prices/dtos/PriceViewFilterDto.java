package com.nm.bridges.prices.dtos;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.bridges.prices.OrderType;
import com.nm.shop.dtos.ShopViewDto;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceViewFilterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderType type;
	private ShopViewDto restaurant;
	private Date from;
	private Date to;
	private boolean hasFrom;
	private boolean hasTo;
	private boolean allRestaurants;
	private boolean allOrders;

	public boolean isAllOrders() {
		return allOrders;
	}

	public void setAllOrders(boolean allOrders) {
		this.allOrders = allOrders;
	}

	public OrderType getType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
	}

	public ShopViewDto getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(ShopViewDto restaurant) {
		this.restaurant = restaurant;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public boolean isHasFrom() {
		return hasFrom;
	}

	public void setHasFrom(boolean hasFrom) {
		this.hasFrom = hasFrom;
	}

	public boolean isHasTo() {
		return hasTo;
	}

	public void setHasTo(boolean hasTo) {
		this.hasTo = hasTo;
	}

	public boolean isAllRestaurants() {
		return allRestaurants;
	}

	public void setAllRestaurants(boolean allRestaurants) {
		this.allRestaurants = allRestaurants;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
