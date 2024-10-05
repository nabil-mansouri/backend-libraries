package com.nm.config.dtos;


import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.nm.config.constants.ModuleConfigKey;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class ModuleConfigDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ModuleConfigKey key;
	private String payload;
	private Date updated;
	private Date created;

	public ModuleConfigKey getKey() {
		return key;
	}

	public void setKey(ModuleConfigKey key) {
		this.key = key;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
