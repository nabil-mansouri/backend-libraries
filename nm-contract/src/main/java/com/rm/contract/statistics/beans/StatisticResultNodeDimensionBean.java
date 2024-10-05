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
public class StatisticResultNodeDimensionBean implements Serializable {

	@Override
	public String toString() {
		return "StatisticResultNodeDimensionBean [type=" + type + ", dimension=" + dimension + ", value=" + value + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DimensionType type;
	private DimensionValue dimension;
	private Object value;

	public DimensionType getType() {
		return type;
	}

	public void setType(DimensionType type) {
		this.type = type;
	}

	public DimensionValue getDimension() {
		return dimension;
	}

	public void setDimension(DimensionValue dimension) {
		this.dimension = dimension;
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
