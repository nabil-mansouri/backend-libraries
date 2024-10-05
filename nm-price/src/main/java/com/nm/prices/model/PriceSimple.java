package com.nm.prices.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.nm.utils.graphs.IGraph;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_price_simple")
public class PriceSimple extends Price implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Transient
	public List<? extends IGraph> childrens() {
		return new ArrayList<IGraph>();
	}
}
