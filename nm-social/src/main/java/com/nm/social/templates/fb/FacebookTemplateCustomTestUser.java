package com.nm.social.templates.fb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.social.facebook.api.GraphApi;
import org.springframework.social.facebook.api.TestUser;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.api.client.util.Strings;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class FacebookTemplateCustomTestUser {

	private String appId;
	private GraphApi graphApi;
	private RestTemplate restTemplate;

	/**
	 * @deprecated Construct with a GraphApi, RestTemplate, and appId instead.
	 * @param restTemplate
	 *            the RestTemplate
	 * @param appId
	 *            the app ID
	 */
	@Deprecated
	public FacebookTemplateCustomTestUser(RestTemplate restTemplate, String appId) {
		this(null, restTemplate, appId);
	}

	public FacebookTemplateCustomTestUser(GraphApi graphApi, RestTemplate restTemplate, String appId) {
		this.graphApi = graphApi;
		this.restTemplate = restTemplate;
		this.appId = appId;
	}

	public List<TestUser> findTestUsers() {
		String url = graphApi.getBaseGraphApiUrl() + "{appId}/accounts/test-users";
		// System.out.println("-------------------------------------------------");
		// System.out.println(restTemplate.getForEntity(url, String.class,
		// appId));
		// System.out.println("-------------------------------------------------");
		// paging => cursors=> before
		// paging => cursors=> after
		// paging => next
		List<TestUser> idList = new ArrayList<TestUser>();
		while (!Strings.isNullOrEmpty(url)) {
			JsonNode responseNode = restTemplate.getForObject(url, JsonNode.class, appId);
			ArrayNode dataNode = (ArrayNode) responseNode.get("data");
			for (JsonNode entryNode : dataNode) {
				String id = entryNode.get("id").textValue();
				String loginUrl = entryNode.get("login_url").textValue();
				String accessToken = entryNode.get("access_token").textValue();
				TestUser u = new TestUser(id, null, null, accessToken, loginUrl);
				idList.add(u);
			}
			if (responseNode.get("paging") != null && responseNode.get("paging").get("next") != null) {
				url = responseNode.get("paging").get("next").textValue();
			} else {
				url = null;
			}
		}
		System.out.println(idList.size());
		return idList;
	}

	public TestUser findTestUsers(String idU) {
		Collection<TestUser> all = findTestUsers();
		for (TestUser s : all) {
			if (s.getId().equals(idU)) {
				return s;
			}
		}
		return null;
	}

}
