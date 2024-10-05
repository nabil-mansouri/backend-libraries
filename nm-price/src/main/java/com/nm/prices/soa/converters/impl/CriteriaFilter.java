package com.nm.prices.soa.converters.impl;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CriteriaFilter<T1, T2> {
	private T1 type;
	private T2 value;
	private boolean isAll;

	public CriteriaFilter() {
	}

	public CriteriaFilter(T1 type, boolean v) {
		setAll(v);
		setType(type);
	}

	public CriteriaFilter(T1 type, T2 val) {
		setValue(val);
		setType(type);
	}

	public T1 getType() {
		return type;
	}

	public void setType(T1 type) {
		this.type = type;
	}

	public T2 getValue() {
		return value;
	}

	public void setValue(T2 value) {
		this.value = value;
	}

	public boolean isAll() {
		return isAll;
	}

	public void setAll(boolean isAll) {
		this.isAll = isAll;
	}

}
