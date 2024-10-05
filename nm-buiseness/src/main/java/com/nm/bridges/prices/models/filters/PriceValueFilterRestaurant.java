package com.nm.bridges.prices.models.filters;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.nm.prices.model.filter.PriceValueFilter;
import com.nm.shop.model.Shop;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_price_value_filter_shop")
public class PriceValueFilterRestaurant extends PriceValueFilter implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Fetch(FetchMode.SELECT)
	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	protected Shop restaurant;

	public Shop getRestaurant() {
		return restaurant;
	}

	public PriceValueFilterRestaurant setRestaurant(Shop restaurant) {
		this.restaurant = restaurant;
		return this;
	}

}
