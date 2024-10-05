package com.nm.bridges.prices.models.filters;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.nm.prices.model.filter.PriceFilter;
import com.nm.shop.model.Shop;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_price_filter_shop")
public class PriceFilterRestaurant extends PriceFilter implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Size(min = 1, message = "price.filter.noshop")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "wrapper")
	protected Set<PriceFilterRestaurantList> list = new HashSet<PriceFilterRestaurantList>();

	public Set<PriceFilterRestaurantList> getList() {
		return list;
	}

	public void setList(Set<PriceFilterRestaurantList> shops) {
		this.list = shops;
	}

	public PriceFilterRestaurant addRestaurants(Collection<Shop> shops) {
		for (Shop r : shops) {
			addRestaurants(r);
		}
		return this;
	}

	public PriceFilterRestaurant addRestaurants(Shop shops) {
		this.list.add(new PriceFilterRestaurantList(shops, this));
		return this;
	}

}
