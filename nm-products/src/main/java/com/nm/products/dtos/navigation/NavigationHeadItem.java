package com.nm.products.dtos.navigation;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.products.constants.NavigationHeadState;
import com.nm.products.dtos.views.ProductPartViewDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.utils.dates.UUIDUtils;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class NavigationHeadItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private ProductViewDto product;
	private ProductViewDto parent;
	private ProductPartViewDto part;
	private boolean root;
	private NavigationHeadState state = NavigationHeadState.Unknwon;
	private String id = UUIDUtils.UUID();

	public ProductViewDto getParent() {
		return parent;
	}

	public void setParent(ProductViewDto parent) {
		this.parent = parent;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public NavigationHeadState getState() {
		return state;
	}

	public void setState(NavigationHeadState state) {
		this.state = state;
	}

	public ProductPartViewDto getPart() {
		return part;
	}

	public void setPart(ProductPartViewDto part) {
		this.part = part;
	}

	public ProductViewDto getProduct() {
		return product;
	}

	public void setProduct(ProductViewDto product) {
		this.product = product;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
