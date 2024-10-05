package com.nm.tests;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BeanTest implements Serializable {
	
	private static final long	serialVersionUID	= 1L;

	private String	name;
	
	private String	description;

	@NotNull(message="{beanTest.name.notNull}")
	@Size(max=50, message="{beanTest.name.size}")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@NotNull(message="{beanTest.description.notNull}")
	@Size(max=300, message="{beanTest.description.size}")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
