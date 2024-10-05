package com.nm.products.dtos.filters;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.nm.utils.hibernate.IQueryRange;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class IngredientFilterDto implements Serializable, IQueryRange {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IngredientFilterDto() {
	}

	public Long getFirst() {
		return first;
	}

	public void setFirst(Long first) {
		this.first = first;
	}

	public Long getCount() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCurrentLocale() {
		return currentLocale;
	}

	public void setCurrentLocale(String currentLocale) {
		this.currentLocale = currentLocale;
	}

	private String currentLocale;
	private Long first;
	private Long limit;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
