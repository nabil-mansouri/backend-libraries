package com.nm.bridges.prices.models.filters;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.nm.shop.model.Shop;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_price_filter_shop_list")
public class PriceFilterRestaurantList implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_rm_price_filter_shop_list", sequenceName = "seq_rm_price_filter_shop_list", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_rm_price_filter_shop_list")
	protected Long id;

	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	protected Shop shop;
	@ManyToOne(optional = false)
	private PriceFilterRestaurant wrapper;

	public PriceFilterRestaurantList() {
	}

	public PriceFilterRestaurantList(Shop e, PriceFilterRestaurant w) {
		setShop(e);
		setWrapper(w);
	}

	public Long getId() {
		return id;
	}

	public PriceFilterRestaurant getWrapper() {
		return wrapper;
	}

	public void setWrapper(PriceFilterRestaurant wrapper) {
		this.wrapper = wrapper;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((shop == null) ? 0 : shop.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PriceFilterRestaurantList other = (PriceFilterRestaurantList) obj;
		if (shop == null) {
			if (other.shop != null)
				return false;
		} else if (!shop.equals(other.shop))
			return false;
		return true;
	}
}
