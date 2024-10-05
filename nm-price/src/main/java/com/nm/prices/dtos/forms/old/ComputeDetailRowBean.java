package com.nm.prices.dtos.forms.old;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.prices.dtos.constants.PriceOperationType;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComputeDetailRowBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double value;
	private PriceOperationType operation;

	// Operation
	public PriceOperationType getOperation() {
		return operation;
	}

	public void setOperation(PriceOperationType operation) {
		this.operation = operation;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}
