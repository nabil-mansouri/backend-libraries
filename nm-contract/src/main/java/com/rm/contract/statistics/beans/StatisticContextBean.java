package com.rm.contract.statistics.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class StatisticContextBean implements Serializable {

	@Override
	public String toString() {
		return "StatisticContextBean [results=" + results + ", filter=" + filter + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Collection<StatisticResultNodeBean> results = new ArrayList<StatisticResultNodeBean>();
	private final StatisticsFilterBean filter;
	private Collection<Long> idDates = new ArrayList<Long>();

	public Collection<Long> getIdDates() {
		return idDates;
	}

	public void setIdDates(Collection<Long> idDates) {
		this.idDates = idDates;
	}

	public StatisticContextBean(StatisticsFilterBean f) {
		this.filter = f;
	}

	public Collection<StatisticResultNodeBean> getResults() {
		return results;
	}

	public void setResults(Collection<StatisticResultNodeBean> results) {
		this.results = results;
	}

	public StatisticsFilterBean getFilter() {
		return filter;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
