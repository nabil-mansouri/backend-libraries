package com.nm.prices.dtos.forms;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.prices.dtos.constants.PriceSelector;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceSelectorBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PriceSelector selector;
	private boolean selected;

	public PriceSelectorBean() {
	}

	public PriceSelectorBean(PriceSelector o) {
		setSelector(o);
	}

	public PriceSelector getSelector() {
		return selector;
	}

	public void setSelector(PriceSelector selector) {
		this.selector = selector;
	}

	public boolean isSelected() {
		return selected;
	}

	public PriceSelectorBean setSelected(boolean selected) {
		this.selected = selected;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
