package com.nm.prices.model.filter;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_price_filter_date")
public class PriceFilterDate extends PriceFilter implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "date_value")
	protected Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date limit) {
		this.date = limit;
	}

}
