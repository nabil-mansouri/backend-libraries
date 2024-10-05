package com.rm.contract.statistics.beans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.nm.app.stats.DimensionType;
import com.nm.app.stats.DimensionValue;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class DimensionValueBean implements Serializable {

	@Override
	public String toString() {
		return "DimensionValueBean [type=" + type + ", dimensionValue=" + dimensionValue + ", value=" + value + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DimensionType type;
	private DimensionValue dimensionValue;
	private Object value;

	public DimensionType getType() {
		return type;
	}

	public void setType(DimensionType type) {
		this.type = type;
	}

	public DimensionValue getDimensionValue() {
		return dimensionValue;
	}

	public void setDimensionValue(DimensionValue dimensionValue) {
		this.dimensionValue = dimensionValue;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
