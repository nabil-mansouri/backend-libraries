package com.nm.products.dtos.navigation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class NavigationStack implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private List<NavigationHeadItem> items = new ArrayList<NavigationHeadItem>();

	public List<NavigationHeadItem> getItems() {
		return items;
	}

	public void setItems(List<NavigationHeadItem> items) {
		this.items = items;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public NavigationHeadItem createItem() {
		NavigationHeadItem item = new NavigationHeadItem();
		items.add(item);
		return item;
	}
}
