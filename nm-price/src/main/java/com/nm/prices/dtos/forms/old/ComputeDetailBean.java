package com.nm.prices.dtos.forms.old;

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
public class ComputeDetailBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double initialVal;
	private Double finalVal;
	private List<ComputeDetailRowBean> details = new ArrayList<ComputeDetailRowBean>();

	public Double getInitialVal() {
		return initialVal;
	}

	public void setInitialVal(Double initialVal) {
		this.initialVal = initialVal;
	}

	public Double getFinalVal() {
		return finalVal;
	}

	public void setFinalVal(Double finalVal) {
		this.finalVal = finalVal;
	}

	public List<ComputeDetailRowBean> getDetails() {
		return details;
	}

	public void setDetails(List<ComputeDetailRowBean> details) {
		this.details = details;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
