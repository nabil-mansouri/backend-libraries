package com.nm.utils.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;
import com.nm.utils.hibernate.NotFoundException;

/**
 * 
 * @author nabilmansouri
 *
 */
public class OptionsList extends ArrayList<ModelOptions> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String locale;
	private Map<String, Serializable> extras = Maps.newHashMap();
	private Map<Class<?>, Class<? extends Dto>> dtoForModels = Maps.newHashMap();
	private Map<Class<?>, Serializable> overrides = Maps.newHashMap();

	@SuppressWarnings("unchecked")
	public <T extends Dto> Class<T> dtoForModel(Class<?> clazz) throws NotFoundException {
		Class<? extends Dto> dto = dtoForModels.get(clazz);
		if (dto == null) {
			throw new NotFoundException("Could not found dto for class:" + clazz);
		}
		return (Class<T>) dto;
	}

	@SuppressWarnings("unchecked")
	public <T extends Dto> Class<T> dtoForModel(Class<?> clazz, Class<? extends Dto> defau) {
		Class<Dto> c = (Class<Dto>) dtoForModels.getOrDefault(clazz, defau);
		return (Class<T>) c;
	}

	public OptionsList pushDtoForModel(Class<?> clazz, Class<? extends Dto> value) {
		dtoForModels.put(clazz, value);
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T getOverrides(Class<? extends T> forClass) {
		return (T) this.overrides.get(forClass);
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> T getOverrides(Class<? extends T> key, T defaultValue) {
		return (T) this.overrides.getOrDefault(key, defaultValue);
	}

	public <T extends Serializable> void pushOverrides(Class<T> forClass, Serializable value) {
		overrides.put(forClass, value);
	}

	public OptionsList put(String key, Serializable v) {
		this.extras.put(key, v);
		return this;
	}

	public boolean contains(String key) {
		return this.extras.containsKey(key);
	}

	public boolean contains(ModelOptions key) {
		return super.contains(key);
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> T get(String key, Class<T> clazz) {
		return (T) this.extras.get(key);
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public boolean hasLocale() {
		return locale != null;
	}

	public OptionsList() {
	}

	public OptionsList withOption(ModelOptions e) {
		super.add(e);
		return this;
	}

	public OptionsList withOption(ModelOptions... e) {
		super.addAll(Arrays.asList(e));
		return this;
	}

	public OptionsList withAddAll(Collection<? extends ModelOptions> c) {
		super.addAll(c);
		return this;
	}

	public OptionsList(String locale) {
		super();
		this.locale = locale;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
