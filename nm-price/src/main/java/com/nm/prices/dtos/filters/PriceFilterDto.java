package com.nm.prices.dtos.filters;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.nm.utils.hibernate.IQueryRange;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class PriceFilterDto implements Serializable, IQueryRange {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Long first;
	private Long count;
	//
	private boolean onlyCurrent;
	private Date from;
	private Date to;
	//

	public Long getFirst() {
		return first;
	}

	public void setFirst(Long first) {
		this.first = first;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public boolean isOnlyCurrent() {
		return onlyCurrent;
	}

	public void setOnlyCurrent(boolean onlyCurrent) {
		this.onlyCurrent = onlyCurrent;
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

	public boolean assertRequired() {
		return true;
	}

	public PriceFilterDto clone() {
		return SerializationUtils.clone(this);
	}
}
