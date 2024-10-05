package com.nm.utils.jdbc;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author mansoun
 *
 */
public interface JdbcCriteria extends Serializable{

	public <T> List<T> getList(Class<T> c);

	public <T> T get(Class<T> clazz);

	public boolean isList();

}