package com.nm.prices.dtos.forms;

import java.io.Serializable;

import org.springframework.context.ApplicationEvent;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceableDeleteEvent extends ApplicationEvent implements Serializable {

	public PriceableDeleteEvent(Object source, Long id) {
		super(source);
		this.idSubject = id;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Long idSubject;

	public Long getIdSubject() {
		return idSubject;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
