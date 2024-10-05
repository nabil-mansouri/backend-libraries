package com.nm.social.templates.fb;

import java.io.Serializable;

import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.support.ClientHttpRequestFactorySelector;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class FacebookTemplateCustom extends FacebookTemplate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FacebookTemplateCustomTestUser template;
	private FacebookTemplateCustomGroup group;
	private FacebookTemplateCustomPage page;
	private FacebookTemplateCustomEvent event;

	private String appId;

	public FacebookTemplateCustom(String accessToken) {
		super(accessToken);
		initialize();
	}

	public FacebookTemplateCustom(String accessToken, String applicationNamespace) {
		super(accessToken, applicationNamespace);
		initialize();
	}

	public FacebookTemplateCustom(String accessToken, String applicationNamespace, String appId) {
		super(accessToken, applicationNamespace, appId);
		this.appId = appId;
		initialize();
	}

	private void initialize() {
		// Wrap the request factory with a BufferingClientHttpRequestFactory so
		// that the error handler can do repeat reads on the response.getBody()
		super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(getRestTemplate().getRequestFactory()));
		initSubApis();
		setApiVersion("2.6");
	}

	private void initSubApis() {
		template = new FacebookTemplateCustomTestUser(this, getRestTemplate(), appId);
		group = new FacebookTemplateCustomGroup(this);
		page = new FacebookTemplateCustomPage(this);
		event = new FacebookTemplateCustomEvent(this);
	}

	public FacebookTemplateCustomEvent customEventTemplateCustom() {
		return event;
	}

	public FacebookTemplateCustomPage customPageTemplateCustom() {
		return page;
	}

	public FacebookTemplateCustomGroup customGroupTemplateCustom() {
		return group;
	}

	public FacebookTemplateCustomTestUser testUserTemplateCustom() {
		return template;
	}
}
