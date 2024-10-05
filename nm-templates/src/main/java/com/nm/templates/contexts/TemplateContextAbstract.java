package com.nm.templates.contexts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */

public abstract class TemplateContextAbstract implements TemplateContext, TemplateContextResults, TemplateContextParameters {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static TemplateContextResults buildDefault() {
		return new TemplateContextDefault();
	}

	private Map<String, Serializable> extra = Maps.newConcurrentMap();

	private Collection<Exception> errors = Lists.newArrayList();

	public TemplateContextAbstract() {
		super();
	}

	public void putAll(TemplateContextResults a) {
		for (String key : a.keysetResult()) {
			this.putResult(key, a.getResult(key));
		}
	}

	public abstract TemplateContext factory();

	public abstract TemplateContext clone();

	public TemplateContextResults createChild(String key) throws Exception {
		putResultIfNotExists(key, new TemplateContextResultsCollection());
		TemplateContextResults res = factory().getResults();
		putResult(key, res);
		return res;
	}

	public TemplateContextResults getChild(String key, int index) throws Exception {
		TemplateContextResultsCollection col = getResult(key);
		return col.get(index);
	}

	public TemplateContextResultsCollection getChildren(String key) throws Exception {
		TemplateContextResultsCollection col = getResult(key);
		return col;
	}

	public Collection<Exception> getErrors() {
		return errors;
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> T getParameter(Class<T> clazz) {
		for (Serializable a : extra.values()) {
			if (clazz.isInstance(a)) {
				return (T) a;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> T getParameter(String key) {
		return (T) this.extra.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> T getParameter(String key, Class<T> clazz) {
		return (T) this.extra.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> T getParameter(String key, T def) {
		return (T) this.extra.getOrDefault(key, def);
	}

	public <T extends Serializable> T getParameterForList(int indexForList, String key) {
		ArrayList<T> map = Lists.newArrayList();
		map = getParameter(key, map);
		return (T) map.get(indexForList);
	}

	public <T extends Serializable> T getParameterForList(int indexForList, String key, T def) {
		ArrayList<T> map = Lists.newArrayList();
		map = getParameter(key, map);
		if (map.size() > indexForList) {
			return (T) map.get(indexForList);
		} else {
			return def;
		}
	}

	public <T extends Serializable> T getParameterForMap(Object keyForMap, String key) {
		HashMap<?, T> map = Maps.newHashMap();
		map = getParameter(key, map);
		return (T) map.get(keyForMap);
	}

	public <T extends Serializable> T getParameterForMap(Object keyForMap, String key, T def) {
		HashMap<?, T> map = Maps.newHashMap();
		map = getParameter(key, map);
		return (T) map.getOrDefault(keyForMap, def);
	}

	public <T extends Serializable> T getResultForMap(Object keyForMap, String key) {
		HashMap<?, T> map = Maps.newHashMap();
		map = getResult(key, map);
		return (T) map.get(keyForMap);
	}

	public <T extends Serializable> T getResultForMap(Object keyForMap, String key, T def) {
		HashMap<?, T> map = Maps.newHashMap();
		map = getResult(key, map);
		return (T) map.getOrDefault(keyForMap, def);
	}

	public TemplateContextParameters getParameters() {
		return this;
	}

	public abstract <T extends Serializable> T getResult(String key);

	public abstract <T extends Serializable> T getResult(String key, T defau);

	public TemplateContextResults getResults() {
		return this;
	}

	public boolean hasErrors() {
		return !this.errors.isEmpty();
	}

	public void pushError(Exception e) {
		this.errors.add(e);
	}

	public <T extends Serializable> T putParameter(String key, T value) {
		extra.put(key, value);
		return value;
	}

	public <T extends Serializable> T putParameter(T value) {
		extra.put(value.getClass().getName(), value);
		return value;
	}

	public abstract <T extends Serializable> T putResult(String key, T value);

	public <T extends Serializable> T putResultIfNotExists(String key, T value) {
		if (keysetResult().contains(key)) {
			return getResult(key);
		} else {
			return putResult(key, value);
		}
	}

}