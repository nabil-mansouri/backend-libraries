package com.rm.contract.statistics.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class StatisticResultNodeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "StatisticResultNodeBean [dimensions=" + dimensions + ", measures=" + measures + "]";
	}

	private List<StatisticResultNodeDimensionBean> dimensions = new ArrayList<StatisticResultNodeDimensionBean>();
	private List<StatisticResultNodeMeasureBean> measures = new ArrayList<StatisticResultNodeMeasureBean>();

	public List<StatisticResultNodeDimensionBean> getDimensions() {
		return dimensions;
	}

	public void setDimensions(List<StatisticResultNodeDimensionBean> dimensions) {
		this.dimensions = dimensions;
	}

	public List<StatisticResultNodeMeasureBean> getMeasures() {
		return measures;
	}

	public void setMeasures(List<StatisticResultNodeMeasureBean> measures) {
		this.measures = measures;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
