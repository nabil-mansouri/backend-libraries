package com.nm.social.templates.google;

import org.springframework.social.google.api.impl.AbstractGoogleApiOperations;
import org.springframework.web.client.RestTemplate;

import com.google.api.services.plus.model.Person;
import com.google.api.services.plusDomains.PlusDomains;
import com.google.api.services.plusDomains.PlusDomains.Circles;

/**
 * @See com.google.api.services.plus.Plus.People.Get
 * @See com.google.api.services.plusDomain.PlusDomain.Circle.List
 * @See org.springframework.social.google.api.plus.impl.PlusTemplate
 * @author Nabil MANSOURI
 *
 */
public class GoogleTemplateCustomPlus extends AbstractGoogleApiOperations {

	private static final String PEOPLE_GET_URL = PlusDomains.DEFAULT_BASE_URL + "people/{userId}";
	private static final String CIRCLE_LIST_URL = PlusDomains.DEFAULT_BASE_URL + "people/{userId}/circles";

	static final String PEOPLE_SEARCH_URL = "https://www.googleapis.com/plus/v1/people";

	protected <T> T getEntity(String url, Class<T> type, Object... urlVariables) {
		return restTemplate.getForObject(url, type, urlVariables);
	}

	public GoogleTemplateCustomPlus(RestTemplate restTemplate, boolean isAuthorized) {
		super(restTemplate, isAuthorized);
	}

	public Person getPerson(String id) {
		return getEntity(PEOPLE_GET_URL, Person.class, id);
	}

	public Person getGoogleProfile() {
		return getPerson("me");
	}

	public Circles listCircles(String id) {
		return getEntity(CIRCLE_LIST_URL, Circles.class, id);
	}
}
