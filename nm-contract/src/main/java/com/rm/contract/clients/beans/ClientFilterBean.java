package com.rm.contract.clients.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.rm.contract.commons.constants.ModelOptions;
import com.rm.utils.dao.IQueryRange;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class ClientFilterBean implements Serializable, IQueryRange {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long first = null;
	private Long limit = null;
	private String text;
	private String reference;
	private boolean hasOptions;
	private List<ModelOptions> options = new ArrayList<ModelOptions>();

	public List<ModelOptions> getOptions() {
		return options;
	}

	public boolean isHasOptions() {
		return hasOptions;
	}

	public void setHasOptions(boolean hasOptions) {
		this.hasOptions = hasOptions;
	}

	public void setOptions(List<ModelOptions> options) {
		this.options = options;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
