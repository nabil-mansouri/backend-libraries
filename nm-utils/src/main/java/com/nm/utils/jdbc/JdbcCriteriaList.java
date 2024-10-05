package com.nm.utils.jdbc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 
 * @author Mansouri Nabil
 *
 */
public class JdbcCriteriaList implements JdbcCriteria {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Serializable> value = new ArrayList<Serializable>();

	public JdbcCriteriaList() {
	}
 
	public JdbcCriteriaList(Serializable... value) {
		super();
		this.value = new ArrayList<Serializable>(Arrays.asList(value));
	}
 
	public JdbcCriteriaList(Collection<? extends Serializable> value) {
		super();
		this.value = new ArrayList<Serializable>(value);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(List<Serializable> value) {
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getList(Class<T> c) {
		return (List<T>) getValue();
	}

	public boolean isList() {
		return true;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz) {
		return (T) getValue();
	}
}
