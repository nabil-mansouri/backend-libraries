package com.nm.prices.model.filter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_price_filter_count")
public class PriceFilterCount extends PriceFilter implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(nullable = false)
	protected int quantity;

	public int getQuantity() {
		return quantity;
	}

	public PriceFilterCount setQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

}
