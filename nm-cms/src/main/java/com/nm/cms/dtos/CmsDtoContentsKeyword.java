package com.nm.cms.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect()
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmsDtoContentsKeyword implements Serializable {

	public CmsDtoContentsKeyword(String text) {
		super();
		this.text = text;
	}

	public CmsDtoContentsKeyword() {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
