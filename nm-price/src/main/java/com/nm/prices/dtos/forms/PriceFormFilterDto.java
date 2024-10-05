package com.nm.prices.dtos.forms;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class PriceFormFilterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Date from;
	private Date to;
	private boolean hasFrom;
	private boolean hasTo;

	public Date getFrom() {
		return from;
	}

	public Date getTo() {
		return to;
	}

	public boolean isHasFrom() {
		return hasFrom;
	}

	public boolean isHasTo() {
		return hasTo;
	}

	public PriceFormFilterDto setFrom(Date from) {
		this.from = from;
		return this;
	}

	public PriceFormFilterDto setHasFrom(boolean hasFrom) {
		this.hasFrom = hasFrom;
		return this;
	}

	public PriceFormFilterDto setHasTo(boolean hasTo) {
		this.hasTo = hasTo;
		return this;
	}

	public PriceFormFilterDto setTo(Date to) {
		this.to = to;
		return this;
	}

}
