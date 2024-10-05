package com.nm.config.dtos;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.nm.config.constants.AppConfigKey;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@JsonAutoDetect
public class ConfigDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private AppConfigKey type;
	private String value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppConfigKey getType() {
		return type;
	}

	public void setType(AppConfigKey type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
