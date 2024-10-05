package com.nm.utils.jdbc;

import java.util.LinkedHashMap;

/**
 * 
 * @author mansoun
 *
 */
public class OrderList extends LinkedHashMap<ISelect, OrderBy> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void add(ISelect select) {
		this.put(select, OrderBy.ASC);
	}
}
