package com.nm.templates.contexts;

import java.io.Serializable;
import java.util.Collection;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public interface TemplateContextParameters extends Serializable {

	void pushError(Exception e);

	boolean hasErrors();

	Collection<Exception> getErrors();

	<T extends Serializable> T putParameter(T value);

	<T extends Serializable> T putParameter(String key, T value);

	<T extends Serializable> T getParameter(String key, Class<T> clazz);

	<T extends Serializable> T getParameter(Class<T> clazz);

	<T extends Serializable> T getParameter(String key);

	<T extends Serializable> T getParameter(String key, T def);

	<T extends Serializable> T getParameterForList(int indexForList, String key);

	<T extends Serializable> T getParameterForList(int indexForList, String key, T def);

	<T extends Serializable> T getParameterForMap(Object keyForMap, String key);

	<T extends Serializable> T getParameterForMap(Object keyForMap, String key, T def);

}