package com.rm.contract.statistics.beans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.nm.app.stats.MeasureType;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class StatisticResultNodeMeasureBean implements Serializable {

	@Override
	public String toString() {
		return "StatisticResultNodeMeasureBean [dimension=" + dimension + ", value=" + value + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MeasureType dimension;
	private Object value;

	public MeasureType getDimension() {
		return dimension;
	}

	public void setDimension(MeasureType dimension) {
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
