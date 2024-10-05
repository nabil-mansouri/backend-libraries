package com.nm.app.log;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * 
 * @author Nabil Mansouri Penser à modifier MessageHeaders
 */
@JsonAutoDetect
public class GeneralLogDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	private GeneralLogLevel level;
	private String message;
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public GeneralLogLevel getLevel() {
		return level;
	}

	public void setLevel(GeneralLogLevel level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
