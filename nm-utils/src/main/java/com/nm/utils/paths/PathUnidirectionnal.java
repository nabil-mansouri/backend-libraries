package com.nm.utils.paths;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class PathUnidirectionnal<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private T item;
	private int index = -1;

	public PathUnidirectionnal<T> push(int index, T item) {
		this.index = index;
		this.item = item;
		return this;
	}

	public boolean founded() {
		return this.item != null;
	}

	public boolean same(PathUnidirectionnal<T> path) {
		if (this.founded() && path.founded()) {
			return this.index == path.getIndex();
		} else {
			return this.founded() == path.founded();
		}
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public T getItem() {
		return item;
	}

	public void setItem(T item) {
		this.item = item;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
