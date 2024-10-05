package com.nm.app.job;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@JsonAutoDetect
public class AppJobDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private AppJobType type;
	private String cron;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppJobType getType() {
		return type;
	}

	public void setType(AppJobType type) {
		this.type = type;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
