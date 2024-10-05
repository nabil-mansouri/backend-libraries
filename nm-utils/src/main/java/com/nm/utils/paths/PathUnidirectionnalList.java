package com.nm.utils.paths;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class PathUnidirectionnalList<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private List<PathUnidirectionnal<T>> list = new ArrayList<PathUnidirectionnal<T>>();

	public PathUnidirectionnalList<T> push(int index, T item) {
		PathUnidirectionnalList<T> p = new PathUnidirectionnalList<T>();
		p.push(index, item);
		return this;
	}

	public int count() {
		int nb = 0;
		for (PathUnidirectionnal<T> p : this.list) {
			if (p.founded()) {
				nb++;
			}
		}
		return nb;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<PathUnidirectionnal<T>> getList() {
		return list;
	}

	public void setList(List<PathUnidirectionnal<T>> list) {
		this.list = list;
	}
}
