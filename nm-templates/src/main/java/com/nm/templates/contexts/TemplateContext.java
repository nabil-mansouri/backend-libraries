package com.nm.templates.contexts;

import java.io.Serializable;
import java.util.Collection;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public interface TemplateContext extends Serializable {

	TemplateContext clone();

	TemplateContextResults getResults();

	TemplateContextParameters getParameters();

	boolean hasErrors();

	Collection<Exception> getErrors();

}