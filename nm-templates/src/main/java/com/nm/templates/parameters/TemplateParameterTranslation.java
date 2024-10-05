package com.nm.templates.parameters;

import java.io.Serializable;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.nm.app.utils.StringMoreUtils;
import com.nm.app.utils.StringMoreUtils.ReplaceResult;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
@Component
public class TemplateParameterTranslation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean ignoreCase;
	public static final String KEY = "TemplateParameterTranslation.KEY";
	private Map<String, String> translation = Maps.newHashMap();

	public TemplateParameterTranslation() {
	}

	public TemplateParameterTranslation(Map<String, String> map) {
		this(map, true);
	}

	public TemplateParameterTranslation(Map<String, String> map, boolean ignoreCase) {
		super();
		this.ignoreCase = ignoreCase;
		for (String key : map.keySet()) {
			put(key, map.get(key));
		}
	}

	public boolean isEmpty() {
		return translation.isEmpty();
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	public void put(String key, String value) {
		translation.put(key, value);
	}

	public ReplaceResult translate(String param) {
		if (ignoreCase) {
			return StringMoreUtils.replaceIgnoreCase(param, translation);
		} else {
			return StringMoreUtils.replace(param, translation);
		}
	}

}
