package com.nm.templates.contexts;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface TemplateContextResults extends Serializable {

	TemplateContextResults cloneResult();

	TemplateContextResults createChild(String key) throws Exception;

	TemplateContextResultsCollection getChildren(String key) throws Exception;

	TemplateContextResults getChild(String key, int index) throws Exception;

	Collection<Exception> getErrors();

	<T extends Serializable> T putParameter(String key, T value);

	<T extends Serializable> T getParameter(Class<T> clazz);

	<T extends Serializable> T getParameter(String key);

	<T extends Serializable> T getParameter(String key, Class<T> clazz);

	<T extends Serializable> T getParameter(String key, T def);

	<T extends Serializable> T getParameterForList(int indexForList, String key);

	<T extends Serializable> T getParameterForList(int indexForList, String key, T def);

	<T extends Serializable> T getParameterForMap(Object keyForMap, String key);

	<T extends Serializable> T getParameterForMap(Object keyForMap, String key, T def);

	<T extends Serializable> T getResult(String key);

	<T extends Serializable> T getResult(String key, T defau);

	<T extends Serializable> T getResultForMap(Object keyForMap, String key);

	<T extends Serializable> T getResultForMap(Object keyForMap, String key, T def);

	boolean hasErrors();

	Set<String> keysetResult();

	void pushError(Exception e);

	<T extends Serializable> T putResult(String key, T value);

	<T extends Serializable> T putResultIfNotExists(String key, T value);

}