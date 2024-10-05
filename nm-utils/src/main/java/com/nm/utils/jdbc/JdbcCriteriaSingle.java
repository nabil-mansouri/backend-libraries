package com.nm.utils.jdbc;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class JdbcCriteriaSingle implements JdbcCriteria {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object value;

	public JdbcCriteriaSingle() {
	}

	public JdbcCriteriaSingle(Object value) {
		super();
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getList(Class<T> c) {
		return (List<T>) Arrays.asList(getValue());
	}

	public boolean isList() {
		return false;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz) {
		return (T) getValue();
	}
}
