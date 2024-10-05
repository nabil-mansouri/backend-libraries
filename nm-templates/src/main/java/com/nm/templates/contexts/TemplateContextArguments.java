package com.nm.templates.contexts;

import java.io.Serializable;
import java.util.Collection;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface TemplateContextArguments extends Serializable {

	void pushError(Exception e);

	boolean hasErrors();

	Collection<Exception> getErrors();

	<T extends Serializable> T getResult(String key, Class<T> clazz);

	<T extends Serializable> T putArgument(String key, T value);

	<T extends Serializable> T getArgument(String key);

	<T extends Serializable> T getArgument(String key, T defau);

}