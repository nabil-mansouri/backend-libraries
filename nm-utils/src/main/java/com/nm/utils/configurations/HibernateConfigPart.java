package com.nm.utils.configurations;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class HibernateConfigPart {

	private String moduleName;
	private List<Class<?>> basePackage = Lists.newArrayList();
	private List<String> sqlScript = Lists.newArrayList();

	public void add(Class<?> c) {
		this.basePackage.add(c);
	}

	public void add(String c) {
		this.sqlScript.add(c);
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public List<Class<?>> getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(List<Class<?>> baseClasses) {
		this.basePackage = baseClasses;
	}

	public List<String> getSqlScript() {
		return sqlScript;
	}

	public void setSqlScript(List<String> sqlScript) {
		this.sqlScript = sqlScript;
	}

}
