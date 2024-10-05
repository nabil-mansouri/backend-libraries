package com.rm.contract.statistics.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.nm.app.stats.DimensionType;
import com.nm.app.stats.DimensionValue;
import com.nm.app.stats.MeasureType;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class StatisticsFilterBean implements Serializable {

	@Override
	public String toString() {
		return "StatisticsFilterBean [idProduct=" + idProduct + ", dimensions=" + dimensions + ", measureTypes=" + measureTypes + ", from=" + from
				+ ", to=" + to + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Collection<Long> idProduct = new ArrayList<Long>();
	private Map<DimensionType, DimensionValue> dimensions = new HashMap<DimensionType, DimensionValue>();
	private Collection<MeasureType> measureTypes = new HashSet<MeasureType>();
	private Collection<Long> idDates = new HashSet<Long>();
	private Collection<StatisticOrderBean> orders = new ArrayList<StatisticOrderBean>();
	private Date from;
	private Date to;
	private Long limit;

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public Collection<StatisticOrderBean> getOrders() {
		return orders;
	}

	public void setOrders(Collection<StatisticOrderBean> orders) {
		this.orders = orders;
	}

	public Collection<Long> getIdDates() {
		return idDates;
	}

	public void setIdDates(Collection<Long> idDates) {
		this.idDates = idDates;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Collection<MeasureType> getMeasureTypes() {
		return measureTypes;
	}

	public void setMeasureTypes(Collection<MeasureType> measureTypes) {
		this.measureTypes = measureTypes;
	}

	public Collection<Long> getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Collection<Long> idProduct) {
		this.idProduct = idProduct;
	}

	public Map<DimensionType, DimensionValue> getDimensions() {
		return dimensions;
	}

	public void setDimensions(Map<DimensionType, DimensionValue> dimensions) {
		this.dimensions = dimensions;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
