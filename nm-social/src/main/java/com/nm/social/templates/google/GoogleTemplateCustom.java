package com.nm.social.templates.google;

import java.io.Serializable;

import org.springframework.social.google.api.impl.GoogleTemplate;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class GoogleTemplateCustom extends GoogleTemplate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GoogleTemplateCustomPlus customPlus;

	public GoogleTemplateCustom() {
		super();
		initialize();
	}

	public GoogleTemplateCustom(String accessToken) {
		super(accessToken);
		initialize();
	}

	private void initialize() {
		customPlus = new GoogleTemplateCustomPlus(getRestTemplate(), isAuthorized());
	}

	public GoogleTemplateCustomPlus customPlusOperations() {
		return customPlus;
	}
}
