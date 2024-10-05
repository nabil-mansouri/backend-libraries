package com.nm.bridges.prices.dtos;

import java.io.Serializable;

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
public class PriceFormNodeFilterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private OrderType type;
	private ShopViewDto restaurant;
	private boolean isAllRestaurants = true;
	private boolean isAllOrders = true;
	private Double value;

	public PriceFormNodeFilterDto() {
	}

	public PriceFormNodeFilterDto(Double value) {
		super();
		this.setValue(value);
	}

	public PriceFormNodeFilterDto(OrderType type, Double value) {
		super();
		this.setAllOrders(false);
		this.setType(type);
		this.setValue(value);
	}

	public PriceFormNodeFilterDto(ShopViewDto type, Double value) {
		super();
		this.setAllRestaurants(false);
		this.setRestaurant(type);
		this.setValue(value);
	}

	public Double getValue() {
		return value;
	}

	public PriceFormNodeFilterDto setValue(Double value) {
		this.value = value;
		return this;
	}

	public OrderType getType() {
		return type;
	}

	public PriceFormNodeFilterDto setType(OrderType type) {
		this.type = type;
		return this;
	}

	public ShopViewDto getRestaurant() {
		return restaurant;
	}

	public PriceFormNodeFilterDto setRestaurant(ShopViewDto restaurant) {
		this.restaurant = restaurant;
		return this;
	}

	public boolean isAllRestaurants() {
		return isAllRestaurants;
	}

	public PriceFormNodeFilterDto setAllRestaurants(boolean isAllRestaurants) {
		this.isAllRestaurants = isAllRestaurants;
		return this;
	}

	public boolean isAllOrders() {
		return isAllOrders;
	}

	public PriceFormNodeFilterDto setAllOrders(boolean isAllOrders) {
		this.isAllOrders = isAllOrders;
		return this;
	}

}
