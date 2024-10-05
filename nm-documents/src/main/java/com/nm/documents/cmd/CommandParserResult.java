package com.nm.documents.cmd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandParserResult {
	private String full;
	private String before;
	private String root;
	private Map<String, List<String>> args = new HashMap<String, List<String>>();

	public String getFull() {
		return full;
	}

	public void setFull(String full) {
		this.full = full;
	}

	public Map<String, List<String>> getArgs() {
		return args;
	}

	public String getBefore() {
		return before;
	}

	public void setArgs(Map<String, List<String>> args) {
		this.args = args;
	}

	public void setBefore(String before) {
		this.before = before;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = safeKey(root);
	}

	protected String safeKey(String key) {
		key = StringUtils.trim(key);
		return key.replaceAll("[^\\p{IsAlphabetic}^\\p{IsDigit}\\-\\_]", "");
	}

	public boolean containsKey(String key) {
		key = safeKey(key);
		return args.containsKey(key);
	}

	public String getFirst(String key) {
		key = safeKey(key);
		return args.get(key).iterator().next();
	}

	public String getFirst(String key, String defaut) {
		key = safeKey(key);
		if (Strings.isNullOrEmpty(args.get(key).iterator().next())) {
			return defaut;
		} else {
			return args.get(key).iterator().next();
		}
	}

	public List<String> getAll(String key) {
		key = safeKey(key);
		return args.get(key);
	}

	public Collection<String> getKeys() {
		return args.keySet();
	}

	public void put(String key, String value) {
		key = safeKey(key);
		value = StringUtils.trim(value);
		if (!args.containsKey(key)) {
			args.put(key, new ArrayList<String>());
		}
		args.get(key).add(value);
	}

}