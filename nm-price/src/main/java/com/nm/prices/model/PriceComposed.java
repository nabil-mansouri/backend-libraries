package com.nm.prices.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_price_composed")
public class PriceComposed extends Price implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	protected List<Price> children = new ArrayList<Price>();

	public List<Price> childrens() {
		return children;
	}

	public List<Price> getChildren() {
		return children;
	}

	public void addChildren(Price price) {
		children.add(price);
	}

	public void setChildren(List<Price> children) {
		this.children = children;
	}

}
