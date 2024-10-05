package com.nm.templates.contexts;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.SerializationUtils;

import com.google.common.collect.Maps;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class TemplateContextDefault extends TemplateContextAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Serializable> arguments = Maps.newConcurrentMap();

	@Override
	public TemplateContext factory() {
		return new TemplateContextDefault();
	}

	@Override
	public TemplateContext clone() {
		return (TemplateContext) SerializationUtils.clone(this);
	}

	public TemplateContextResults cloneResult() {
		return (TemplateContextResults) clone();
	}

	public <T extends Serializable> T putResult(String key, T value) {
		arguments.put(key, value);
		return value;
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> T getResult(String key) {
		return (T) this.arguments.get(key);
	}

	public Set<String> keysetResult() {
		return this.arguments.keySet();
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> T getResult(String key, T defau) {

		T temp = (T) this.arguments.get(key);
		if (temp == null) {
			return defau;
		}
		return temp;
	}
}
