package com.nm.dictionnary.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.nm.dictionnary.constants.EnumDictionnaryOperation;
import com.nm.dictionnary.constants.EnumDictionnaryState;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class DtoDictionnaryValueDefault implements DtoDictionnaryValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idValue;
	private String value;
	private Integer valueInteger;
	private Double valueDouble;
	private Date valueDate;
	private EnumDictionnaryState state;
	private EnumDictionnaryOperation operation = EnumDictionnaryOperation.Nothing;

	public DtoDictionnaryValueDefault() {
	}

	public DtoDictionnaryValueDefault(Long idValue) {
		super();
		this.idValue = idValue;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public Double getValueDouble() {
		return valueDouble;
	}

	public Integer getValueInteger() {
		return valueInteger;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public void setValueDouble(Double valueDouble) {
		this.valueDouble = valueDouble;
	}

	public DtoDictionnaryValueDefault setValueInteger(Integer valueInteger) {
		this.valueInteger = valueInteger;
		return this;
	}

	public EnumDictionnaryState getState() {
		return state;
	}

	public void setState(EnumDictionnaryState state) {
		this.state = state;
	}

	public Long getIdValue() {
		return idValue;
	}

	public void setIdValue(Long idValue) {
		this.idValue = idValue;
	}

	public String getValue() {
		return value;
	}

	public DtoDictionnaryValueDefault setValue(String value) {
		this.value = value;
		return this;
	}

	public EnumDictionnaryOperation getOperation() {
		return operation;
	}

	public void setOperation(EnumDictionnaryOperation operation) {
		this.operation = operation;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
