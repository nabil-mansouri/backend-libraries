package com.rm.contract.addresses.beans;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CountryBean {
	private String code;
	private String name;
	private boolean selected;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
