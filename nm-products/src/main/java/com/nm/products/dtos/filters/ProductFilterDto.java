package com.nm.products.dtos.filters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.nm.products.constants.ProductType;
import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.hibernate.IQueryRange;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class ProductFilterDto implements Serializable, IQueryRange {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String currentLocale;

	private Long first;
	private Long count;
	private ProductType type;
	private Long idCategory;
	private Long id;
	private Collection<Long> ids = new ArrayList<Long>();
	private Collection<ModelOptions> options = new ArrayList<ModelOptions>();

	public Collection<Long> getIds() {
		return ids;
	}

	public void setIds(Collection<Long> ids) {
		this.ids = ids;
	}

	public Collection<ModelOptions> getOptions() {
		return options;
	}

	public void setOptions(Collection<ModelOptions> options) {
		this.options = options;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductFilterDto() {
	}

	public Long getCount() {
		return count;
	}

	public String getCurrentLocale() {
		return currentLocale;
	}

	public Long getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(Long idCategory) {
		this.idCategory = idCategory;
	}

	public Long getFirst() {
		return first;
	}

	public ProductType getType() {
		return type;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public void setCurrentLocale(String currentLocale) {
		this.currentLocale = currentLocale;
	}

	public void setFirst(Long first) {
		this.first = first;
	}

	public void setType(ProductType type) {
		this.type = type;
	}
}
