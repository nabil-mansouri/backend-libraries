package com.nm.datas.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@JsonAutoDetect
public class MemoryKeyDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Object source;
	private final String category;
	private final String key;

	public MemoryKeyDto(Object source, String key) {
		this(source, "ALL", key);
	}

	public MemoryKeyDto(Object source, String category, Long key) {
		this(source, category, (key != null) ? key.toString() : null);
	}

	public MemoryKeyDto(Object source, String category, String key) {
		super();
		this.source = source;
		this.category = category;
		this.key = key;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Object getSource() {
		return source;
	}

	public String getScope() {
		return source.getClass().getName();
	}

	public String getCategory() {
		return category;
	}

	public String getKey() {
		return key;
	}

}
