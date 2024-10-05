package com.nm.products.dtos.forms;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.BooleanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.products.dtos.views.ProductViewDto;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductPartFormDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Long id;
	private String name;
	private String uuid = UUID.randomUUID().toString();
	private int facultatif;
	private boolean selected;
	private Map<Long, Boolean> products = new HashMap<Long, Boolean>();

	public ProductPartFormDto() {
	}

	public Map<Long, Boolean> getProducts() {
		return products;
	}

	public void setProducts(Map<Long, Boolean> products) {
		this.products = products;
	}

	public ProductPartFormDto select(ProductViewDto cat) {
		this.products.put(cat.getId(), true);
		return this;
	}

	public boolean isSelected() {
		return selected;
	}

	public ProductPartFormDto setSelected(boolean selected) {
		this.selected = selected;
		return this;
	}

	public ProductPartFormDto add(ProductFormDto subprodSaved1) {
		ProductViewDto def = new ProductViewDto();
		def.setId(subprodSaved1.getId());
		this.add(def);
		return this;
	}

	public int getFacultatif() {
		return facultatif;
	}

	public ProductPartFormDto setFacultatif(int facultatif) {
		this.facultatif = facultatif;
		return this;
	}

	public ProductPartFormDto withMandatory(boolean mandatory) {
		boolean facultatif = !mandatory;
		setFacultatif(BooleanUtils.toInteger(facultatif, 1, 0));
		return this;
	}

	public String getUuid() {
		return uuid;
	}

	public ProductPartFormDto setUuid(String uuid) {
		this.uuid = uuid;
		return this;
	}

	public ProductPartFormDto add(ProductViewDto e) {
		products.put(e.getId(), true);
		return this;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ProductPartFormDto setId(Long id) {
		this.id = id;
		return this;
	}

	public ProductPartFormDto setName(String name) {
		this.name = name;
		return this;
	}

	public void select(Long id) {
		this.products.put(id, true);
	}

	public boolean isSelected(Long id2) {
		return products.containsKey(id2) && products.get(id2);
	}

	public void clearAll() {
		this.products.clear();
	}

	public void selectAll(Set<Long> intersection) {
		for (Long l : intersection) {
			this.select(l);
		}
	}

}
