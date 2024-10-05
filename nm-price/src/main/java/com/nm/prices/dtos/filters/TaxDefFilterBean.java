package com.nm.prices.dtos.filters;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class TaxDefFilterBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TaxDefFilterBean() {
	}

	public Long getFirst() {
		return first;
	}

	public void setFirst(Long first) {
		this.first = first;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Long first ;
	private Long count ;

}
