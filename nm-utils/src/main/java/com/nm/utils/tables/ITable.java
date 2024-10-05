package com.nm.utils.tables;

import java.util.List;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public interface ITable<T> {
	public List<? extends List<T>> cells();

	public List<T> createRow();
}
