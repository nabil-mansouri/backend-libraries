package com.rm.contract.discounts.beans;

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
public class DiscountCommunicationContentBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lang;
	private String content;
	private String contentText;

	public DiscountCommunicationContentBean() {
	}

	public String getLang() {
		return lang;
	}

	public DiscountCommunicationContentBean setLang(String lang) {
		this.lang = lang;
		return this;
	}

	public String getContent() {
		return content;
	}

	public DiscountCommunicationContentBean setContent(String content) {
		this.content = content;
		return this;
	}

	public String getContentText() {
		return contentText;
	}

	public DiscountCommunicationContentBean setContentText(String contentText) {
		this.contentText = contentText;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
