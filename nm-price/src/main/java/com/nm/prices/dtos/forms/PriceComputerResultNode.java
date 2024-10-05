package com.nm.prices.dtos.forms;

import java.io.Serializable;

import com.nm.prices.dtos.constants.PriceOperationType;

/***
 * 
 * @author nabilmansouri
 *
 */
public class PriceComputerResultNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PriceOperationType operation;
	private Double value;
	private Long idPriceValue;
	private PriceSubjectBean subject;

	public PriceComputerResultNode() {
	}

	public PriceComputerResultNode(PriceOperationType operation, Double value, PriceSubjectBean subject) {
		super();
		this.operation = operation;
		this.value = value;
		this.subject = subject;
	}

	public PriceComputerResultNode(PriceOperationType operation, Double value, Long id, PriceSubjectBean subject) {
		super();
		this.operation = operation;
		this.value = value;
		this.idPriceValue = id;
		this.subject = subject;
	}

	public Long getIdPriceValue() {
		return idPriceValue;
	}

	public void setIdPriceValue(Long idPriceValue) {
		this.idPriceValue = idPriceValue;
	}

	public PriceOperationType getOperation() {
		return operation;
	}

	public PriceSubjectBean getSubject() {
		return subject;
	}

	public void setSubject(PriceSubjectBean subject) {
		this.subject = subject;
	}

	public Double getValue() {
		return value;
	}

	public void setOperation(PriceOperationType operation) {
		this.operation = operation;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}
