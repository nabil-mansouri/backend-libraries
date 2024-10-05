package com.nm.bridges.prices.dtos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.nm.bridges.prices.OrderType;
import com.nm.prices.dtos.forms.PriceFormFilterDto;
import com.nm.shop.dtos.ShopViewDto;

/**
 * 
 * @author nabilmansouri
 *
 */
@JsonTypeName("CustomPriceFormFilterDto")
public class CustomPriceFormFilterDto extends PriceFormFilterDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Collection<OrderTypeFormDto> types = new HashSet<OrderTypeFormDto>();
	private Collection<ShopViewDto> restaurants = new ArrayList<ShopViewDto>();
	private boolean isAllOrders;
	private boolean isAllRestaurants;

	public CustomPriceFormFilterDto select(ShopViewDto e) {
		this.restaurants.add(e.clone().setSelected(true));
		return this;
	}

	public CustomPriceFormFilterDto select(OrderType e) {
		OrderTypeFormDto t = new OrderTypeFormDto();
		t.setOrderType(e);
		t.setSelected(true);
		this.types.add(t);
		return this;
	}

	public Collection<ShopViewDto> getRestaurants() {
		return restaurants;
	}

	public Collection<OrderTypeFormDto> getTypes() {
		return types;
	}

	public OrderTypeFormDto findBy(OrderType type) {
		for (OrderTypeFormDto t : types) {
			if (t.getOrderType().equals(type)) {
				return t;
			}
		}
		return null;
	}

	public boolean isAllOrders() {
		return isAllOrders;
	}

	public boolean isAllRestaurants() {
		return isAllRestaurants;
	}

	public CustomPriceFormFilterDto setAllOrders(boolean allOrders) {
		this.isAllOrders = allOrders;
		return this;
	}

	public CustomPriceFormFilterDto setAllRestaurants(boolean allRestaurants) {
		this.isAllRestaurants = allRestaurants;
		return this;
	}

	public CustomPriceFormFilterDto setRestaurants(Collection<ShopViewDto> restaurants) {
		this.restaurants = restaurants;
		return this;
	}

	public CustomPriceFormFilterDto setTypes(Collection<OrderTypeFormDto> types) {
		this.types = types;
		return this;
	}

	public CustomPriceFormFilterDto withRestaurants(ShopViewDto restaurant) {
		this.restaurants.add(restaurant);
		return this;
	}
}
