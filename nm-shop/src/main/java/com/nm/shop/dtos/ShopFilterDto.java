package com.nm.shop.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.nm.utils.hibernate.IQueryRange;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class ShopFilterDto implements Serializable, IQueryRange {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShopFilterDto() {
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

	public Long getId() {
		return id;
	}

	public ShopFilterDto setId(Long id) {
		this.id = id;
		return this;
	}

	private Long first;
	private Long count;
	private Long id;

}
